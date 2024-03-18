package com.seafood.inventory.inventory.event;


import com.seafood.inventory.entities.dto.transaction.TransactionRequestDTO;

public record InventoryCheckRequestEvent(TransactionRequestDTO transactionDTO) {
    // No additional methods needed if you're just encapsulating the TransactionDTO
}
