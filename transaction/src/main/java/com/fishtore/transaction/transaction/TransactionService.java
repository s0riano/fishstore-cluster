package com.fishtore.transaction.transaction;


import dto.TransactionDTO;
import dto.TransactionRequestDTO;
import dto.payload.InventoryResponsePayload;
import dto.preorder.PreOrderDTO;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    Transaction saveTransaction(Transaction transaction);

    String processOrderPlacement(TransactionDTO transactionDTO);

    String processPreOrder (PreOrderDTO preOrderDTO);

    Transaction updateTransactionStatus(UUID transactionId, TransactionProcessingStatus status);
    Transaction findTransactionById(UUID transactionId);

    void requestItemAvailability(TransactionRequestDTO transactionRequestDTO); //not made yet

    void sendInventoryCheckMessage(Transaction transaction);

    void processInventoryResponse(InventoryResponsePayload responsePayload);

    List<Transaction> getAllTransactions();

    Transaction getTransactionById(UUID id);
    public void updatePickupTimestamp(UUID transactionId);
}
