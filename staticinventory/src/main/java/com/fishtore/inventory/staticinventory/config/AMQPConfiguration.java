package com.fishtore.inventory.staticinventory.config;

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

    @Bean
    public Queue inventoryCheckQueue(
            @Value("${inventory.check.queue}") final String queueName) {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding bindingInventoryCheckQueue(
            Queue inventoryCheckQueue, DirectExchange inventoryExchange) {
        return BindingBuilder.bind(inventoryCheckQueue)
                .to(inventoryExchange)
                .with("check.inventory"); // Routing key for inventory check requests
    }

    // If the inventory service also sends responses, you would need a queue and binding for that as well.
}

