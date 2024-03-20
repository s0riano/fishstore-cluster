package com.fishtore.transaction.crossserviceoperations.preorder;

import com.fishtore.transaction.transaction.Transaction;

public class TransactionCreationResponse {
    private final Transaction transaction;
    private final String errorMessage;

    private TransactionCreationResponse(Transaction transaction, String errorMessage) {
        this.transaction = transaction;
        this.errorMessage = errorMessage;
    }

    public static TransactionCreationResponse withTransaction(Transaction transaction) {
        return new TransactionCreationResponse(transaction, null);
    }

    public static TransactionCreationResponse withErrorMessage(String errorMessage) {
        return new TransactionCreationResponse(null, errorMessage);
    }

    public boolean hasError() {
        return errorMessage != null;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
