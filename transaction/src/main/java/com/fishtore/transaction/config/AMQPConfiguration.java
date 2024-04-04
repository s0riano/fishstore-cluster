package com.fishtore.transaction.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfiguration {

    @Bean
    public DirectExchange inventoryExchange(
            @Value("${inventory.exchange}") final String exchangeName) {
        return ExchangeBuilder.directExchange(exchangeName).durable(true).build();
    }

    // This queue is for the transaction service to listen to responses from the inventory service
    @Bean
    public Queue inventoryResponseQueue(
            @Value("${inventory.response.queue}") final String queueName) {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding bindingInventoryResponseQueue(
            Queue inventoryResponseQueue, DirectExchange inventoryExchange) {
        return BindingBuilder.bind(inventoryResponseQueue)
                .to(inventoryExchange)
                .with("inventory.response"); // Routing key for inventory responses
    }

    // New Queue for pre-order checks ==========================================================

    @Bean
    public Queue inventoryPreOrderCheckQueue(
            @Value("${inventory.preorder.queue}") final String queueName) {
        return QueueBuilder.durable(queueName).build();
    }

    // Binding for the pre-order check queue
    @Bean
    public Binding bindingInventoryPreOrderCheckQueue(
            Queue inventoryPreOrderCheckQueue, DirectExchange inventoryExchange,
            @Value("${inventory.preorder.routing.key}") final String routingKey) {
        return BindingBuilder.bind(inventoryPreOrderCheckQueue)
                .to(inventoryExchange)
                .with(routingKey); // Routing key for pre-order checks
    }

}


