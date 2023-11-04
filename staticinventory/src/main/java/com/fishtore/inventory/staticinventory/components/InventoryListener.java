package com.fishtore.inventory.staticinventory.components;

import com.fishtore.inventory.staticinventory.dto.TransactionDTO;
import com.fishtore.inventory.staticinventory.dto.TransactionRequestDTO;
import com.fishtore.inventory.staticinventory.inventory.InventoryService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryListener {

    private final InventoryService inventoryService;

    public InventoryListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @RabbitListener(queues = "${inventory.check.queue}")
    public void receiveMessage(TransactionDTO transactionDTO) {
        // Process the received message using the InventoryService
        inventoryService.checkAndReserveInventory(transactionDTO);
    }
}
