package com.seafood.inventory.staticInventory.operations.addInventory;

import com.seafood.inventory.staticInventory.Inventory;
import com.seafood.inventory.staticInventory.StaticInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AddInventoryHandler {

    private final StaticInventoryRepository staticInventoryRepository;

    @Autowired
    public AddInventoryHandler(StaticInventoryRepository staticInventoryRepository) {
        this.staticInventoryRepository = staticInventoryRepository;
    }

    public Inventory handle(Inventory inventory, UUID storeId) {
        inventory.setShopId(storeId);
        inventory.setLastUpdated(LocalDateTime.now());
        return staticInventoryRepository.save(inventory);
    }
}
