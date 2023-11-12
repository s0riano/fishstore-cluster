package com.fishtore.price.components;

import com.fishtore.price.dto.PriceEntryDTO;
import com.fishtore.price.service.PriceVerificationService;
import com.fishtore.price.transaction.PriceStatus;
import com.fishtore.price.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PriceVerificationComponent {

    private final PriceVerificationService priceVerificationService;

    @Autowired
    public PriceVerificationComponent(PriceVerificationService priceVerificationService) {
        this.priceVerificationService = priceVerificationService;
    }

    public PriceStatus verifyTransactionPrices(Transaction transaction, Long sellerId) {
        if (transaction == null || transaction.getItems() == null) {
            throw new IllegalArgumentException("Transaction and its items cannot be null");
        }

        List<PriceEntryDTO> priceEntries = transaction.getItems().stream()
                .map(item -> new PriceEntryDTO(item.getSeafoodType(), item.getPricePerKilo()))
                .collect(Collectors.toList());

        // Call the price verification service with the price entries and sellerId
        return priceVerificationService.verifyPrices(priceEntries, sellerId);
    }
}

