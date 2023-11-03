package com.fishtore.transaction;

import com.fishtore.transaction.dto.TransactionDTO;
import com.fishtore.transaction.pricing.PriceVerificationService;
import com.fishtore.transaction.transaction.Transaction;
import com.fishtore.transaction.transaction.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderPlacementService {

    private static final Logger logger = LoggerFactory.getLogger(OrderPlacementService.class);

    @Autowired
    private TransactionRepository transactionRepository; // Assuming you have a repository to save transactions

    @Autowired
    private PriceVerificationService priceVerificationService;

    public String handleOrderPlacement(TransactionDTO transactionDTO) {
        // Convert DTO to Transaction
        Transaction transaction = convertDtoToTransaction(transactionDTO);

        // Verify price
        boolean isPriceCorrect = priceVerificationService.verifyTransactionPrice(transaction);
        if (!isPriceCorrect) {
            logger.error("Price verification failed for transaction ID: {}", transaction.getTransactionId());
            return "Order placement failed due to price discrepancy.";
        }

        // Save the transaction (or other required logic)
        transactionRepository.save(transaction);

        return "Order placed with ID: " + transaction.getTransactionId() + ". Awaiting confirmation.";
    }

    private Transaction convertDtoToTransaction(TransactionDTO transactionDTO) {
        // Conversion logic here
        // ...
        return transaction; // return the converted transaction
    }
}

