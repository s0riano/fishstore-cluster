package com.fishtore.price.service;

import com.fishstore.shared.dto.TransactionRequestDTO;
import com.fishstore.shared.dto.payload.InventoryResponsePayload;

import com.fishtore.price.components.InventoryResponseComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQService.class);
    private final InventoryResponseComponent inventoryResponseComponent;
    private final RabbitTemplate rabbitTemplate;
    private final MessageConverter messageConverter;

    //private final AppConfig appConfig;

    @Value("${inventory.exchange}")
    private String inventoryExchange; // Use the same property for the exchange

    @Autowired
    public RabbitMQService(RabbitTemplate rabbitTemplate,
                           @Qualifier("jsonMessageConverter") MessageConverter messageConverter,
                           InventoryResponseComponent inventoryResponseComponent) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageConverter = messageConverter;
        this.inventoryResponseComponent = inventoryResponseComponent;
    }

    public void sendInventoryCheckMessage(TransactionRequestDTO transactionRequestDTO) {
        rabbitTemplate.setMessageConverter(messageConverter); // Convert the DTO to a message (e.g., JSON) using the injected messageConverter
        rabbitTemplate.convertAndSend(inventoryExchange, "check.inventory", transactionRequestDTO);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${inventory.response.queue}", durable = "true"), // Use a property for the queue name
                    exchange = @Exchange(value = "${inventory.exchange}", type = ExchangeTypes.DIRECT), // Use the same property for the exchange
                    key = "inventory.response") // Consistent routing key
    )
    public void processInventoryResponse(InventoryResponsePayload responsePayload) {
        String transactionId = responsePayload.getTransactionId();
        boolean isAvailable = responsePayload.isAvailable();
        logger.info("Received inventory response: Transaction ID = {}, Available = {}", transactionId, isAvailable);
        inventoryResponseComponent.updateStatusFromInventory(responsePayload);
    }
}
