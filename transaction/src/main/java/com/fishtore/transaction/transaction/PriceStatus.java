package com.fishtore.transaction.transaction;

public enum PriceStatus {
    NOT_CHECKED,
    PRICE_VERIFIED_AND_MATCHES,
    PRICE_VERIFICATION_FAILED, //Could be used if the asynchronous price verification itself failed (e.g., due to the pricing service being down).
    SUSPECTED_TAMPERING, //Might not be needed yet, but indicates that the system suspects the data might have been tampered with.
    ERROR,
    PRICE_MISMATCH, //Indicates that the price provided by the frontend didn't match the verified price.
    SERVERSIDE_ERROR,
    API_CALL_ISSUE,
    API_CALL_ERROR,
    CANT_FIND_ITEM,
    INVALID_SEAFOOD_TYPE,
    CALCULATION_ERROR,
    CANT_FIND_INVENTORY_PRICE, //Cant find the price or inventory, might not have that in inventory

}
