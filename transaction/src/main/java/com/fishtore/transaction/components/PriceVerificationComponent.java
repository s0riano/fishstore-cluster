package com.fishtore.transaction.components;

import com.fishtore.transaction.service.PriceVerificationServiceImpl;
import com.fishtore.transaction.transaction.PriceStatus;
import com.fishtore.transaction.transaction.Transaction;
import dto.PriceEntryDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PriceVerificationComponent {

    private final PriceVerificationServiceImpl priceVerificationService;

    public PriceVerificationComponent(PriceVerificationServiceImpl priceVerificationService) {
        this.priceVerificationService = priceVerificationService;
    }

    public PriceStatus verifyTransactionPrices(Transaction transaction, UUID shopId) {
        if (transaction == null || transaction.getItems() == null) {
            throw new IllegalArgumentException("Transaction and its items cannot be null");
        }

        List<PriceEntryDTO> priceEntries = transaction.getItems().stream()
                .map(item -> new PriceEntryDTO(item.getSeafoodType(), item.getPricePerKilo()))
                .collect(Collectors.toList());

        // Call the price verification service with the price entries and sellerId
        return priceVerificationService.verifyPrices(priceEntries, shopId);
    }
}

