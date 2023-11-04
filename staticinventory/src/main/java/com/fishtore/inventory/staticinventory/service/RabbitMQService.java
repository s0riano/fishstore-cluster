package com.fishtore.inventory.staticinventory.service;

import com.fishtore.inventory.staticinventory.components.InventoryCheckComponent;
import com.fishtore.inventory.staticinventory.dto.TransactionDTO;
import com.fishtore.inventory.staticinventory.payload.InventoryResponsePayload;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class RabbitMQService {

    @Value("${inventory.exchange}")
    private String inventoryExchange;
    private final RabbitTemplate rabbitTemplate;
    private final InventoryCheckComponent inventoryCheckComponent; // Add this line

    @Autowired
    public RabbitMQService(RabbitTemplate rabbitTemplate, InventoryCheckComponent inventoryCheckComponent) { // Modify this constructor
        this.rabbitTemplate = rabbitTemplate;
        this.inventoryCheckComponent = inventoryCheckComponent; // Set the component
    }


    // This method is used to send responses back to the transaction service
    public void sendInventoryResponse(InventoryResponsePayload responsePayload) {
        // Convert the response payload to a message (e.g., JSON) if necessary
        rabbitTemplate.convertAndSend(inventoryExchange, "inventory.response", responsePayload); // Consistent routing key
    }


    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${inventory.check.queue}", durable = "true"), // Use a property for the queue name
                    exchange = @Exchange(value = "${inventory.exchange}", type = ExchangeTypes.DIRECT), // Use the same property for the exchange
                    key = "check.inventory" // Consistent routing key
            )
    )   // Listener method to process inventory check messages from the transaction service
    public void processInventoryCheckMessage(TransactionDTO transactionDTO) {
        // Delegate the inventory check to the InventoryCheckComponent
        inventoryCheckComponent.processOrder(transactionDTO);
    }
}