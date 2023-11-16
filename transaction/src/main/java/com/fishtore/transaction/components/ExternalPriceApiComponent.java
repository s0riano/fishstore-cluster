package com.fishtore.transaction.components;

import com.fishstore.shared.dto.SeafoodType;
import com.fishtore.transaction.dto.PriceEntryDTO;

import java.util.List;

import com.fishtore.transaction.dto.PriceResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class ExternalPriceApiComponent { //not set up yet

    private final RestTemplate restTemplate;
    private final String priceApiUrl;

    @Autowired
    public ExternalPriceApiComponent(RestTemplate restTemplate, @Value("${external.price.api.url}") String priceApiUrl) {
        this.restTemplate = restTemplate;
        this.priceApiUrl = priceApiUrl;
    }

    public List<PriceEntryDTO> getCurrentPrices(Long sellerId) {
        String url = String.format("%s/prices/%s", priceApiUrl, sellerId);
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
                        // Handle the invalid seafood type as per your requirement
                        // For example, throw an exception or return an empty list
                    }
                }
            }

        } catch (Exception e) {
            log.error("Error fetching prices", e);
        }

        return priceResponse != null ? priceResponse.getPrices() : List.of();
    }
}
