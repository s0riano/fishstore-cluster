package com.seafood.inventory.service;

import com.seafood.inventory.entities.dto.transaction.InventoryResponsePayload;
import com.seafood.inventory.entities.dto.transaction.TransactionRequestDTO;
import com.seafood.inventory.inventory.event.InventoryCheckEvent;
import com.seafood.inventory.inventory.event.InventoryCheckRequestEvent;
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
        //log.info("Received inventory response: {}", responsePayload);
        // Convert the response payload to a message (e.g., JSON) if necessary
        rabbitTemplate.convertAndSend(inventoryExchange, "inventory.response", responsePayload); // Consistent routing key
    }


   /* @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${inventory.check.queue}", durable = "true"),
                    exchange = @Exchange(value = "${inventory.exchange}", type = ExchangeTypes.DIRECT),
                    key = "check.inventory"
            )
    )*/
    public void processInventoryCheckMessage(TransactionRequestDTO transactionDTO) {
        log.info("Received inventory check message: {}", transactionDTO);
        log.info("Reading from RabbitMQService.processInventoryCheckMessage()");
        // Publish an event that InventoryCheckComponent listens to
        eventPublisher.publishEvent(new InventoryCheckRequestEvent(transactionDTO));
    }

    @EventListener
    public void onInventoryCheckEvent(InventoryCheckEvent event) {
        sendInventoryResponse(event.responsePayload());
    }
}