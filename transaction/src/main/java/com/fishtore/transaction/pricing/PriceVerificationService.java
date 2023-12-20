package com.fishtore.transaction.pricing;

import com.fishtore.transaction.dto.TransactionRequestDTO;
import com.fishtore.transaction.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class PriceVerificationService {

    private static final Logger logger = LoggerFactory.getLogger(PriceVerificationService.class);
    private final RestTemplate restTemplate;
    private static final String GATEWAY_PRICE_ENDPOINT = "http://localhost:8000/api/inventory/prices/{sellerId}";

    @Autowired
    public PriceVerificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public CompletableFuture<Boolean> verifyTransactionPriceAsync(Transaction transaction) {
        return CompletableFuture.supplyAsync(() -> {
            boolean isPriceCorrect = false;
            //Map<String, BigDecimal> sellerPrices = fetchSellerPrices(requestDTO);

            /*for (TransactionItemDTO itemDTO : requestDTO.getItems()) {
                BigDecimal actualPrice = sellerPrices.get(itemDTO.getSeafoodType());
                if (actualPrice == null) {
                    isPriceCorrect = false;
                    break;
                }

                BigDecimal expectedTotalPriceForItem = itemDTO.getKilos().multiply(actualPrice);
                if (expectedTotalPriceForItem.compareTo(itemDTO.getPricePerKilo()) != 0) {
                    isPriceCorrect = false;
                    break;
                }
            }*/

            //logger.info("Price verification for transaction ID: {} - {}", requestDTO.getTransactionId(), isPriceCorrect ? "Success" : "Failed");
            return isPriceCorrect;
        });
    }

    private Map<String, BigDecimal> fetchSellerPrices(TransactionRequestDTO requestDTO) {
        String url = GATEWAY_PRICE_ENDPOINT.replace("{sellerId}", requestDTO.getShopId().toString());
        ResponseEntity<Map<String, BigDecimal>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, BigDecimal>>() {}
        );
        return response.getBody();
    }


}

