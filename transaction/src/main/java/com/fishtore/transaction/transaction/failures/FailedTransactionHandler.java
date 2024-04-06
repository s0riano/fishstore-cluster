package com.fishtore.transaction.transaction.failures;

import com.fishtore.transaction.transaction.TransactionProcessingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class FailedTransactionHandler {

    private final FailedTransactionRepository failedTransactionRepository;

    @Autowired
    public FailedTransactionHandler(FailedTransactionRepository failedTransactionRepository) {
        this.failedTransactionRepository = failedTransactionRepository;
    }

    public void logFailedTransaction(UUID transactionId, TransactionProcessingStatus status, String errorMessage) {
        FailedTransaction failedTransaction = new FailedTransaction();
        failedTransaction.setTransactionId(transactionId.toString());
        failedTransaction.setStatus(status);
        failedTransaction.setFailedAt(LocalDateTime.now());
        failedTransaction.setErrorMessage(errorMessage);
        failedTransactionRepository.save(failedTransaction);
    }
}
