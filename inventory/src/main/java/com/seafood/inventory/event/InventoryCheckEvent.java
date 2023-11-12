package com.seafood.inventory.event;


import com.fishstore.shared.dto.payload.InventoryResponsePayload;

public record InventoryCheckEvent(InventoryResponsePayload responsePayload) {
    // You can add additional methods or override methods here if necessary
}

