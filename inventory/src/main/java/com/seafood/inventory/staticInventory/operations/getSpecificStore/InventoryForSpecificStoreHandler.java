package com.seafood.inventory.staticInventory.operations.getSpecificStore;

import com.seafood.inventory.entities.dto.staticInventory.InventoryForSpecificShopDTO;
import com.seafood.inventory.staticInventory.Inventory;
import com.seafood.inventory.staticInventory.StaticInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class InventoryForSpecificStoreHandler {

    private final StaticInventoryRepository staticInventoryRepository;

    @Autowired
    public InventoryForSpecificStoreHandler(StaticInventoryRepository staticInventoryRepository) {
        this.staticInventoryRepository = staticInventoryRepository;
    }

    public void handle(UUID shopId) {
        List<Inventory> inventories = staticInventoryRepository.findAllByShopId(shopId);
        InventoryForSpecificShopDTO inventory = new InventoryForSpecificShopDTO();

        InventoryForSpecificShopDTO dto = mapInventoriesToDTO(inventories);
    }

    private InventoryForSpecificShopDTO mapInventoriesToDTO(List<Inventory> allInventories) {
        // Implement the logic to map inventories to InventoryForSpecificShopDTO
        // This might include converting the List<Inventory> to the desired format
        // Return the mapped DTO
        return null; // Placeholder return
    }

    public void calculateRemainingKilos(UUID storeId, LocalDate date) {

    }
}