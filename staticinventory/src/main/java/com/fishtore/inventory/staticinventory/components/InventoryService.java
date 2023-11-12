package com.fishtore.inventory.staticinventory.components;

import com.fishstore.shared.dto.SeafoodType;
import com.fishstore.shared.dto.TransactionItemDTO;
import com.fishstore.shared.dto.TransactionRequestDTO;
import com.fishtore.inventory.staticinventory.inventory.Inventory;
import com.fishtore.inventory.staticinventory.inventory.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class InventoryService { //remebmer to rename method

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public boolean checkAndReserveInventory(TransactionRequestDTO transactionRequestDTO) {
        for (TransactionItemDTO item : transactionRequestDTO.getItems()) {
            SeafoodType type;
            try {
                type = item.getSeafoodType();
            } catch (IllegalArgumentException e) {
                log.error("The seller, =name=, does not this item in his inventory");
                return false;
            }

            Inventory inventory = inventoryRepository.findBySellerIdAndSeafoodType(
                    transactionRequestDTO.getSellerId(), type);

            if (inventory == null || inventory.getKilos().compareTo(item.getKilos()) < 0) {
                return false;
            }

            inventory.setKilos(inventory.getKilos().subtract(item.getKilos()));
            inventoryRepository.save(inventory);
        }

        return true;
    }
}

