package com.fishtore.inventory.staticinventory.components;

import com.fishtore.inventory.staticinventory.dto.TransactionDTO;
import com.fishtore.inventory.staticinventory.dto.TransactionItemDTO;
import com.fishtore.inventory.staticinventory.inventory.Inventory;
import com.fishtore.inventory.staticinventory.inventory.InventoryService;
import com.fishtore.inventory.staticinventory.inventory.SeafoodType;
import com.fishtore.inventory.staticinventory.payload.InventoryResponsePayload;
import com.fishtore.inventory.staticinventory.service.RabbitMQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class InventoryCheckComponent {

    private static final Logger logger = LoggerFactory.getLogger(InventoryCheckComponent.class);

    private final InventoryService inventoryService;
    private final RabbitMQService rabbitMQService;

    @Autowired
    public InventoryCheckComponent(InventoryService inventoryService ,RabbitMQService rabbitMQService) {
        this.inventoryService = inventoryService;
        this.rabbitMQService = rabbitMQService;
    }

    @Async
    public void processOrder(TransactionDTO transactionDTO){
        logger.info("Received order ID: {}", transactionDTO.getTransactionId());

        InventoryResponsePayload responsePayload = new InventoryResponsePayload(
                transactionDTO.getTransactionId(),
                calculateAvailability(transactionDTO)
        );
        //need to make a function to remove the items that were sold
        rabbitMQService.sendInventoryResponse(responsePayload);
    }

    public boolean calculateAvailability(TransactionDTO transactionDTO) {
        for (TransactionItemDTO item : transactionDTO.getItems()) {
            Inventory inventory = inventoryService.findBySellerIdAndSeafoodType(
                    transactionDTO.getSellerId(), SeafoodType.valueOf(item.getSeafoodType()));

            // If inventory is null or not enough, return false immediately
            if (inventory == null || inventory.getKilos().compareTo(item.getKilos()) < 0) {
                return false;
            }
        }

        return true;
    }
}
