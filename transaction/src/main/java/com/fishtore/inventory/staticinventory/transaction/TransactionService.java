package com.fishtore.inventory.staticinventory.transaction;

import com.fishtore.inventory.staticinventory.dto.TransactionDTO;

public interface TransactionService {


    Transaction createTransaction(Transaction transaction);
    Transaction updateTransactionStatus(Long transactionId, TransactionStatus status);
    Transaction findTransactionById(Long transactionId);

    void requestItemAvailability(TransactionDTO transactionDTO); //not made yet
}
