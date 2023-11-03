package com.fishtore.transaction.staticinventory.transaction;

import com.fishtore.transaction.staticinventory.dto.TransactionDTO;
import com.fishtore.transaction.staticinventory.dto.TransactionRequestDTO;
import com.fishtore.transaction.staticinventory.payload.InventoryResponsePayload;

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
