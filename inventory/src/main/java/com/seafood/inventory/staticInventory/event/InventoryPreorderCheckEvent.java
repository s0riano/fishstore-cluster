package com.seafood.inventory.staticInventory.event;

import com.seafood.inventory.entities.dto.transaction.InventoryResponsePayload;

public record InventoryPreorderCheckEvent(InventoryResponsePayload responsePayload) {
}
