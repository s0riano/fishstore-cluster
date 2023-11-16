package com.fishtore.transaction.transaction;

import com.fishstore.shared.dto.TransactionDTO;
import com.fishstore.shared.dto.TransactionRequestDTO;
import com.fishstore.shared.dto.payload.InventoryResponsePayload;

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
