package com.seafood.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitConfig {
    @Bean
    public DirectExchange sellerExchange() {
        return new DirectExchange("sellerExchange");
    }

    @Bean
    public Queue validateSellerQueue() {
        return new Queue("validate-seller-queue", true);
    }

    @Bean
    public Queue ownerValidationResponseQueue() {
        return new Queue("seller-validation-response-queue", true);
    }

    @Bean
    public Binding binding(Queue validateSellerQueue, DirectExchange sellerExchange ){
        return BindingBuilder.bind(validateSellerQueue).to(sellerExchange).with("#");
    }
}