package com.seafood.inventory.components;

import com.seafood.inventory.dto.transaction.InventoryResponsePayload;
import com.seafood.inventory.dto.transaction.TransactionRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InventoryListener {

    private final TransactionProcessorComponent transactionProcessorComponent;

    private final InventoryCheckComponent inventoryCheckComponent;

    public InventoryListener(TransactionProcessorComponent transactionProcessorComponent,
                             InventoryCheckComponent inventoryCheckComponent) {
        this.transactionProcessorComponent = transactionProcessorComponent;
        this.inventoryCheckComponent = inventoryCheckComponent;
    }

    @RabbitListener(queues = "${inventory.check.queue}")
    public void receiveMessage(TransactionRequestDTO transactionDTO) {
        log.info("Received transactionDTO from: TransactionService, it contains = {}", transactionDTO);
        boolean isInventoryAvailable = transactionProcessorComponent.processTransaction(transactionDTO);
        log.info("Is inventory available: {}", isInventoryAvailable);

        InventoryResponsePayload inventoryResponsePayload = new InventoryResponsePayload(
               transactionDTO.getTransactionId(),
               isInventoryAvailable
        );

        log.info("InventoryResponsePayload, it looks like this = {}", inventoryResponsePayload);
        inventoryCheckComponent.processOrder(transactionDTO);
    }
}
