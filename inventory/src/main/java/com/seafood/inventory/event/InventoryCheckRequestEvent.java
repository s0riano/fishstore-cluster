package com.seafood.inventory.event;


import com.seafood.inventory.dto.transaction.TransactionRequestDTO;

public record InventoryCheckRequestEvent(TransactionRequestDTO transactionDTO) {
    // No additional methods needed if you're just encapsulating the TransactionDTO
}
