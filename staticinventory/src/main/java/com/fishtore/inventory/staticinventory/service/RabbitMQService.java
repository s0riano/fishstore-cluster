package com.fishtore.inventory.staticinventory.service;

import com.fishtore.inventory.staticinventory.dto.TransactionRequestDTO;
import com.fishtore.inventory.staticinventory.dto.payload.InventoryResponsePayload;
import com.fishtore.inventory.staticinventory.event.InventoryCheckEvent;
import com.fishtore.inventory.staticinventory.event.InventoryCheckRequestEvent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

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