package com.seafood.inventory.config;

import lombok.Value;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfiguration {


    @Bean // Define the TopicExchange to send responses back to the Transaction Service
    public TopicExchange inventoryExchange(
            @Value("${inventory.exchange}") final String exchangeName) {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    // Define the Queue where the Inventory Service listens for inventory check requests
    @Bean
    public Queue inventoryCheckQueue(
            @Value("${inventory.check.queue}") final String queueName) {
        return QueueBuilder.durable(queueName).build();
    }

    // Define the Binding between the Exchange and the Queue for inventory check requests
    @Bean
    public Binding bindingInventoryCheckQueue(Queue inventoryCheckQueue, TopicExchange inventoryExchange) {
        return BindingBuilder.bind(inventoryCheckQueue)
                .to(inventoryExchange)
                .with("check.inventory"); // This is the routing key for inventory check requests
    }
}
