package com.seafood.inventory.event;

import com.fishstore.shared.dto.TransactionRequestDTO;

public record InventoryCheckRequestEvent(TransactionRequestDTO transactionDTO) {
    // No additional methods needed if you're just encapsulating the TransactionDTO
}
