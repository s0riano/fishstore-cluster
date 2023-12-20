package com.fishtore.transaction.pricing;

import com.fishtore.transaction.transaction.TransactionItem;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PriceCalculationService {

    private final String INVENTORY_SERVICE_URL = "http://inventory-service-url/api"; // Update with your Inventory service endpoint
    private final RestTemplate restTemplate;

    public PriceCalculationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches the base price for a specific seafood type from the Inventory service.
     */
    public BigDecimal fetchBasePriceForSeafood(String seafoodType) {
        // Use the RestTemplate to make an API call to the Inventory service to get the base price.
        return restTemplate.getForObject(INVENTORY_SERVICE_URL + "/price/" + seafoodType, BigDecimal.class);
    }

    /**
     * Calculates the final price for a list of items.
     */
    public BigDecimal calculateFinalPrice(List<TransactionItem> items) {
        BigDecimal total = BigDecimal.ZERO;

        for (TransactionItem item : items) {
            BigDecimal basePrice = fetchBasePriceForSeafood(String.valueOf(item.getSeafoodType()));
            BigDecimal itemTotal = basePrice.multiply(item.getKilos());

            // Apply any other pricing logic here (e.g., discounts, taxes, etc.)
            total = total.add(itemTotal);
        }
        return total;
    }
}






