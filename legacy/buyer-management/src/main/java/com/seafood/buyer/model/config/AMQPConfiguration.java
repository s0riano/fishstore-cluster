package com.seafood.buyer.model.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfiguration {

    @Bean
    public DirectExchange sellerExchange(
            @Value("${seller.exchange}") final String exchangeName) {
        return ExchangeBuilder.directExchange(exchangeName).durable(true).build();
    }

    // This queue is for the buyer service to listen to messages from the seller service
    @Bean
    public Queue buyerCheckQueue(
            @Value("${buyer.check.queue}") final String queueName) {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding bindingBuyerCheckQueue(
            Queue buyerCheckQueue, DirectExchange sellerExchange) {
        return BindingBuilder.bind(buyerCheckQueue)
                .to(sellerExchange)
                .with("check.buyer"); // Routing key for messages from seller service
    }
}
