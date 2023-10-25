package com.fishtore.inventory.staticinventory.transaction;

public enum TransactionStatus {
    PENDING,
    COMPLETED,
    CANCELLED,
    FAILED,
    DECLINED,
    PROCEEDING, //? -instead of pending
    //ACCEPTED // - need a status for the accepted/ok status type orders
}