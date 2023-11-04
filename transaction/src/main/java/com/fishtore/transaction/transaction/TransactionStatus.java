package com.fishtore.transaction.transaction;

public enum TransactionStatus {
    PENDING,
    COMPLETED,
    CANCELLED,
    FAILED,
    DECLINED,
    INSUFFICIENT_INVENTORY, //Indicates that the order cannot be fulfilled due to lack of inventory.
    PROCEEDING, //? -instead of pending
    CONFIRMED, //accepted ?
    PRICE_MISMATCH, //Indicates that the price provided by the frontend didn't match the verified price.
    PRICE_VERIFICATION_FAILED, //Could be used if the asynchronous price verification itself failed (e.g., due to the pricing service being down).
    SUSPECTED_TAMPERING, //Might not be needed yet, but indicates that the system suspects the data might have been tampered with.
    SERVERSIDE_ERROR, //SOMETHING HAVE HAPPEND ON THE SERVERSIDE, LIKE CALCULATION ERROR
    CALCULATION_ERROR

    //ACCEPTED // - need a status for the accepted/ok status type orders
}