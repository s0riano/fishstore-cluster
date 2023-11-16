package com.fishtore.transaction.transaction;

public enum TransactionStatus {
    PENDING, //A Transactions init. Its just made. Should not be an available transaction. Maybe the seller should be able to look in to it manually?
    COMPLETED,
    CANCELLED,
    FAILED,
    DECLINED,
    RESERVED,
    INSUFFICIENT_INVENTORY, //Indicates that the order cannot be fulfilled due to lack of inventory.
    PROCEEDING, //? -instead of pending
    CONFIRMED, //accepted ?
    PRICE_MISMATCH, //Indicates that the price provided by the frontend didn't match the verified price.
    PRICE_VERIFICATION_FAILED, //Could be used if the asynchronous price verification itself failed (e.g., due to the pricing service being down).
    SUSPECTED_TAMPERING, //Might not be needed yet, but indicates that the system suspects the data might have been tampered with.
    SERVERSIDE_ERROR, //SOMETHING HAVE HAPPENED ON THE SERVERSIDE, LIKE CALCULATION ERROR
    CALCULATION_ERROR,
    ERROR, //Something weird happened
    CANT_FIND_TRANSACTION_ID,
    NO_RESPONSE_FROM_INVENTORY, //maybe the inventory is offline?
    //ACCEPTED // - need a status for the accepted/ok status type orders
}