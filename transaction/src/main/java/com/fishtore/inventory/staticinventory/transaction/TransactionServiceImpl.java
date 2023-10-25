package com.fishtore.inventory.staticinventory.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        // Here, you might add any business logic needed before saving a transaction
        // For example, validating the transaction, checking inventory, etc.

        // Save the transaction to the database
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction updateTransactionStatus(Long transactionId, TransactionStatus status) {
        // Fetch the transaction from the database
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Update the transaction's status
        transaction.setStatus(status);

        // You might also want to add logic here for when a transaction is completed or cancelled,
        // such as adjusting inventory, sending notifications, etc.

        // Save the updated transaction back to the database
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction findTransactionById(Long transactionId) {
        // Fetch and return the transaction from the database
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    // Implement other methods as needed
}

