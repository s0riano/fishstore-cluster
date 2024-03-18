package com.seafood.inventory.config.shared;

public class CatchNotFoundException extends RuntimeException {
    public CatchNotFoundException(String message) {
        super(message);
    }
}
