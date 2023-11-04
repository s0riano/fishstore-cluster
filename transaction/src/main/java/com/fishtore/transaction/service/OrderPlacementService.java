package com.fishtore.transaction.service;

import com.fishtore.transaction.dto.TransactionDTO;
import com.fishtore.transaction.dto.TransactionRequestDTO;
import com.fishtore.transaction.pricing.PriceVerificationService;
import com.fishtore.transaction.transaction.Transaction;
import com.fishtore.transaction.transaction.TransactionItem;
import com.fishtore.transaction.transaction.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class OrderPlacementService {

    private static final Logger logger = LoggerFactory.getLogger(OrderPlacementService.class);
    private final TransactionRepository transactionRepository;
    private final PriceVerificationService priceVerificationService;

    public OrderPlacementService(TransactionRepository transactionRepository, PriceVerificationService priceVerificationService) {
        this.transactionRepository = transactionRepository;
        this.priceVerificationService = priceVerificationService;
    }

    public String handleOrderPlacement(Transaction transaction) {

        // Asynchronous price verification
        CompletableFuture<Boolean> priceVerificationFuture = priceVerificationService.verifyTransactionPriceAsync(transaction);


        priceVerificationFuture.thenAccept(isPriceCorrect -> {
            if (isPriceCorrect) {
                // Save the transaction
                transactionRepository.save(transaction);
                logger.info("Order placed with ID: {}. Awaiting confirmation.", transaction.getTransactionId());
            } else {
                logger.error("Price verification failed for transaction ID: {}", transaction.getTransactionId());
            }
        });

        return "Order processing for ID: " + transaction.getTransactionId() + ". Please wait for confirmation.";
    }

    private Transaction convertDtoToTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        //transaction.setTransactionId(transactionDTO.getTransactionId());
        transaction.setSellerId(transactionDTO.getSellerId());
        transaction.setBuyerId(transactionDTO.getBuyerId());
        // ... set other fields from DTO to Transaction ...

        List<TransactionItem> items = transactionDTO.getItems().stream()
                .map(dtoItem -> {
                    TransactionItem item = new TransactionItem();
                    item.setSeafoodType(dtoItem.getSeafoodType());
                    item.setKilos(dtoItem.getKilos());
                    // ... set other fields from DTO item to TransactionItem ...
                    return item;
                })
                .collect(Collectors.toList());
        transaction.setItems(items);

        return transaction;
    }

}


