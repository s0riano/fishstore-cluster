package com.fishtore.transaction.transaction;

import com.fishtore.transaction.dto.TransactionDTO;
import com.fishtore.transaction.dto.TransactionRequestDTO;
import com.fishtore.transaction.dto.payload.InventoryResponsePayload;

import java.util.List;

public interface TransactionService {

    Transaction saveTransaction(Transaction transaction);

    public String processOrderPlacement(TransactionDTO transactionDTO);

    Transaction updateTransactionStatus(String transactionId, TransactionStatus status);
    Transaction findTransactionById(String transactionId);

    void requestItemAvailability(TransactionRequestDTO transactionRequestDTO); //not made yet

    void sendInventoryCheckMessage(Transaction transaction);

    void processInventoryResponse(InventoryResponsePayload responsePayload);

    List<Transaction> getAllTransactions();
}
