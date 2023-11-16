package com.seafood.inventory.components;

import com.seafood.inventory.dto.transaction.InventoryResponsePayload;
import com.seafood.inventory.dto.transaction.TransactionRequestDTO;
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
