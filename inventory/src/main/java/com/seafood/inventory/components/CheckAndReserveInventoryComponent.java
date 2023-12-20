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

import java.math.BigDecimal;

@Component
@Slf4j
public class CheckAndReserveInventoryComponent {

    private final InventoryCalculator inventoryCalculator;

    @Autowired
    public CheckAndReserveInventoryComponent(InventoryCalculator inventoryCalculator) {
        this.inventoryCalculator = inventoryCalculator;
    }

    @Transactional
    public boolean checkAndReserveInventory(TransactionRequestDTO transactionRequestDTO) {
        for (TransactionItemDTO item : transactionRequestDTO.getItems()) {
            // Use the mapper to convert the DTO type to the local enum
            SeafoodType type = SeafoodTypeMapper.mapToLocalSeafoodType(item.getSeafoodType().name());

            BigDecimal currentInventory = inventoryCalculator.calculateCurrentInventory(transactionRequestDTO.getShopId(), type);
            BigDecimal requiredKilos = item.getKilos();

            if (currentInventory.compareTo(requiredKilos) < 0) {
                log.error("Insufficient inventory for seller {} and item {}", transactionRequestDTO.getShopId(), type);
                return false;
            }

        }
        return true;
    }
}
