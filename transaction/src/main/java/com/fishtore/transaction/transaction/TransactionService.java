package com.fishtore.transaction.transaction;

import com.fishtore.transaction.dto.TransactionDTO;
import com.fishtore.transaction.dto.TransactionRequestDTO;
import com.fishtore.transaction.dto.payload.InventoryResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    Transaction saveTransaction(Transaction transaction);

    public String processOrderPlacement(TransactionDTO transactionDTO);

    Transaction updateTransactionStatus(UUID transactionId, TransactionStatus status);
    Transaction findTransactionById(UUID transactionId);

    void requestItemAvailability(TransactionRequestDTO transactionRequestDTO); //not made yet

    void sendInventoryCheckMessage(Transaction transaction);

    void processInventoryResponse(InventoryResponsePayload responsePayload);

    List<Transaction> getAllTransactions();

    Transaction getTransactionById(UUID id);
    public void updatePickupTimestamp(UUID transactionId);
}
