package com.fishtore.inventory.staticinventory.components;

import com.fishtore.inventory.staticinventory.dto.TransactionRequestDTO;
import com.fishtore.inventory.staticinventory.dto.payload.InventoryResponsePayload;
import com.fishtore.inventory.staticinventory.inventory.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InventoryListener {

    private final InventoryService inventoryService;
    private final InventoryCheckComponent inventoryCheckComponent;

    public InventoryListener(InventoryService inventoryService, InventoryCheckComponent inventoryCheckComponent) {
        this.inventoryService = inventoryService;
        this.inventoryCheckComponent = inventoryCheckComponent;
    }

    @RabbitListener(queues = "${inventory.check.queue}")
    public void receiveMessage(TransactionRequestDTO transactionDTO) {
        // Process the received message using the InventoryService
        log.info("Received transactionDTO from: TransactionService, it contains = {}", transactionDTO);
        boolean isInventoryAvailable = inventoryService.checkAndReserveInventory(transactionDTO);

        InventoryResponsePayload inventoryResponsePayload = new InventoryResponsePayload(
               transactionDTO.getTransactionId(),
               isInventoryAvailable
        );

        log.info("InventoryResponsePayload, it looks like this = {}", inventoryResponsePayload);
        inventoryCheckComponent.processOrder(transactionDTO);
    }
}
