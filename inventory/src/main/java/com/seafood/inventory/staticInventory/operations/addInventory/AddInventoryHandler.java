package com.seafood.inventory.staticInventory.operations.addInventory;

import com.seafood.inventory.staticInventory.Inventory;
import com.seafood.inventory.staticInventory.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AddInventoryHandler {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public AddInventoryHandler(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory handle(Inventory inventory, UUID storeId) {
        inventory.setStoreId(storeId);
        inventory.setLastUpdated(LocalDateTime.now());
        return inventoryRepository.save(inventory);
    }
}
