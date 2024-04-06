package com.seafood.seller.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange sellerExchange(
            @Value("${seller.exchange}") final String exchangeName) {
        return ExchangeBuilder.directExchange(exchangeName).durable(true).build();
    }

    // This queue is for the transaction service to listen to responses from the inventory service
    @Bean
    public Queue sellerResponseQueue(
            @Value("${seller.response.queue}") final String queueName) {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding bindingSellerResponseQueue(
            Queue inventoryResponseQueue, DirectExchange inventoryExchange) {
        return BindingBuilder.bind(inventoryResponseQueue)
                .to(inventoryExchange)
                .with("seller.response"); // Routing key for seller responses
    }
}

