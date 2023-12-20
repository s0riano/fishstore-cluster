package com.seafood.inventory.shared;

public class CatchNotFoundException extends RuntimeException {
    public CatchNotFoundException(String message) {
        super(message);
    }
}
