package com.seafood.inventory.inventory.event;


import com.seafood.inventory.entities.dto.transaction.InventoryResponsePayload;

public record InventoryCheckEvent(InventoryResponsePayload responsePayload) {
    // You can add additional methods or override methods here if necessary
}

