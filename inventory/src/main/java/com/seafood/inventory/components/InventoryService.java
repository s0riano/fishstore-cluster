package com.seafood.inventory.components;

import com.seafood.inventory.dto.transaction.TransactionItemDTO;
import com.seafood.inventory.dto.transaction.TransactionRequestDTO;
import com.seafood.inventory.enums.SeafoodType;
import com.seafood.inventory.inventory.components.InventoryCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.seafood.inventory.mapper.SeafoodTypeMapper;

@Component
@Slf4j
public class InventoryService {

    private final InventoryCalculator inventoryCalculator;

    @Autowired
    public InventoryService(InventoryCalculator inventoryCalculator) {
        this.inventoryCalculator = inventoryCalculator;
    }

    @Transactional
    public boolean checkAndReserveInventory(TransactionRequestDTO transactionRequestDTO) {
        for (TransactionItemDTO item : transactionRequestDTO.getItems()) {
            // Use the mapper to convert the DTO type to the local enum
            SeafoodType type = SeafoodTypeMapper.mapToLocalSeafoodType(item.getSeafoodType().name());

            float currentInventory = inventoryCalculator.calculateCurrentInventory(transactionRequestDTO.getSellerId(), type);

            if (currentInventory < item.getKilos().floatValue()) {
                log.error("Insufficient inventory for seller {} and item {}", transactionRequestDTO.getSellerId(), type);
                return false;
            }

            // Rest of your code...
        }
        return true;
    }
}
