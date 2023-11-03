package com.fishtore.transaction.staticinventory.transaction;

public enum TransactionStatus {
    PENDING,
    COMPLETED,
    CANCELLED,
    FAILED,
    DECLINED,
    PROCEEDING, //? -instead of pending
    CONFIRMED
    //ACCEPTED // - need a status for the accepted/ok status type orders
}