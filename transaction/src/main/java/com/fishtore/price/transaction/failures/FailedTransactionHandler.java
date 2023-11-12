package com.fishtore.price.transaction.failures;

import com.fishtore.price.transaction.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FailedTransactionHandler {

    private final FailedTransactionRepository failedTransactionRepository;

    @Autowired
    public FailedTransactionHandler(FailedTransactionRepository failedTransactionRepository) {
        this.failedTransactionRepository = failedTransactionRepository;
    }

    public void logFailedTransaction(String transactionId, TransactionStatus status, String errorMessage) {
        FailedTransaction failedTransaction = new FailedTransaction();
        failedTransaction.setTransactionId(transactionId);
        failedTransaction.setStatus(status);
        failedTransaction.setFailedAt(LocalDateTime.now());
        failedTransaction.setErrorMessage(errorMessage);
        failedTransactionRepository.save(failedTransaction);
    }
}
