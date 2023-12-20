package com.fishstore.fishstoreservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //store service now
public class RabbitConfig {

    @Bean
    public Queue sellerQueue() {
        return new Queue("sellerQueue", true);
    }

    @Bean
    public DirectExchange sellerExchange() {
        return new DirectExchange("sellerExchange");
    }

    @Bean
    public Binding binding(Queue sellerQueue, DirectExchange sellerExchange) {
        return BindingBuilder.bind(sellerQueue).to(sellerExchange).with("seller.created");
    }

    @Bean
    public Queue validateSellerQueue() {
        return new Queue("validate-seller-queue", true);
    }

    @Bean
    public Queue sellerValidationResponseQueue() {
        return new Queue("seller-validation-response-queue", true);
    }
}
