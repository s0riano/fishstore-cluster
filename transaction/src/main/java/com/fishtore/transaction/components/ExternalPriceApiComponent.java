package com.fishtore.transaction.components;



import java.util.List;
import java.util.UUID;


import dto.PriceEntryDTO;
import dto.PriceResponseDTO;
import enums.SeafoodType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class ExternalPriceApiComponent { // continue to fix the id string

    private final RestTemplate restTemplate;
    private final String priceApiUrl;

    @Autowired
    public ExternalPriceApiComponent(RestTemplate restTemplate,
                                     @Value("${external.price.api.url}") String priceApiUrl) {
        this.restTemplate = restTemplate;
        this.priceApiUrl = priceApiUrl;
    }

    public List<PriceEntryDTO> getCurrentPrices(UUID shopId) {
        String url = String.format("%s/prices/%s", priceApiUrl, shopId);
        //log.info("URL: {}",url);
        PriceResponseDTO priceResponse = null;

        try {
            priceResponse = restTemplate.getForObject(url, PriceResponseDTO.class);
            log.info("Prices received: {}", priceResponse);

            // Check if all seafood types are valid enum constants
            if (priceResponse != null) {
                for (PriceEntryDTO entry : priceResponse.getPrices()) {
                    if (!EnumUtils.isValidEnum(SeafoodType.class, entry.getSeafoodType().name())) {
                        log.error("Invalid seafood type received: {}", entry.getSeafoodType());
                        // handle invalid seafood type
                        // throw an exception or return an empty list
                    }
                }
            }

        } catch (Exception e) {
            log.error("Error fetching prices", e);
        }

        return priceResponse != null ? priceResponse.getPrices() : List.of();
    }
}
