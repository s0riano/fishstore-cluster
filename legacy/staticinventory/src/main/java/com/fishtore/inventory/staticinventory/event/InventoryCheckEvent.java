package com.fishtore.inventory.staticinventory.event;

import com.fishtore.inventory.staticinventory.dto.payload.InventoryResponsePayload;

public record InventoryCheckEvent(InventoryResponsePayload responsePayload) {
    // You can add additional methods or override methods here if necessary
}

