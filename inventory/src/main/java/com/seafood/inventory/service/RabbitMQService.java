package com.seafood.inventory.service;

import com.seafood.inventory.entities.dto.transaction.InventoryResponsePayload;
import com.seafood.inventory.entities.dto.transaction.TransactionRequestDTO;
import com.seafood.inventory.inventory.event.InventoryCheckEvent;
import com.seafood.inventory.inventory.event.InventoryCheckRequestEvent;
import com.seafood.inventory.staticInventory.event.InventoryPreorderCheckEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQService {

    @Value("${inventory.exchange}")
    private String inventoryExchange;
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public RabbitMQService(RabbitTemplate rabbitTemplate, ApplicationEventPublisher eventPublisher) {
        this.rabbitTemplate = rabbitTemplate;
        this.eventPublisher = eventPublisher;
    }


    // This method is used to send responses back to the transaction service
    public void sendInventoryResponse(InventoryResponsePayload responsePayload) {
        // Convert the response payload to a message (e.g., JSON) if necessary
        rabbitTemplate.convertAndSend(
                inventoryExchange,
                "inventory.response",
                responsePayload
        ); // Consistent routing key
    }

    public void sendInventoryPreorderResponse(InventoryResponsePayload responsePayload) {
        rabbitTemplate.convertAndSend(
                inventoryExchange,
                "inventory.preorder.response",
                responsePayload
        );
    }

    @EventListener
    public void onInventoryCheckEvent(InventoryCheckEvent event) {
        sendInventoryResponse(event.responsePayload());
    }
    @EventListener
    public void onInventoryPreorderCheckEvent(InventoryPreorderCheckEvent event) {
        sendInventoryPreorderResponse(event.responsePayload());
    }
}