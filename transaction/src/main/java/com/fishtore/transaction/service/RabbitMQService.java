package com.fishtore.transaction.service;

import com.fishtore.transaction.dto.TransactionRequestDTO;
import com.fishtore.transaction.dto.payload.InventoryResponsePayload;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${inventory.exchange}")
    private String inventoryExchange; // Use the same property for the exchange

    public RabbitMQService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendInventoryCheckMessage(TransactionRequestDTO transactionRequestDTO) {
        // Convert the DTO to a message (e.g., JSON) if necessary
        // For simplicity, we're assuming the DTO can be sent as-is
        rabbitTemplate.convertAndSend(inventoryExchange, "check.inventory", transactionRequestDTO);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${inventory.response.queue}", durable = "true"), // Use a property for the queue name
                    exchange = @Exchange(value = "${inventory.exchange}", type = ExchangeTypes.DIRECT), // Use the same property for the exchange
                    key = "inventory.response") // Consistent routing key
    )
    public void processInventoryResponse(InventoryResponsePayload responsePayload) {
        Long transactionId = responsePayload.getTransactionId();
        boolean isAvailable = responsePayload.isAvailable();

        // Handle the response //create new component (what), might use status
        // For example: Update the transaction based on the inventory check response
    }
}
