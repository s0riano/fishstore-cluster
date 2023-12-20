package com.seafood.inventory.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import com.seafood.inventory.dto.transaction.TransactionRequestDTO;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


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

    @Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();

        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("TransactionRequestDTO", TransactionRequestDTO.class); // No need to import from other service
        classMapper.setIdClassMapping(idClassMapping);

        classMapper.setTrustedPackages("*");
        converter.setClassMapper(classMapper);
        return converter;
    }

    /*@Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();

        // Define the mapping between the typeId header and the expected type
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("TransactionMessagePayload", TransactionRequestDTO.class); // Adjust the mapping as needed
        classMapper.setIdClassMapping(idClassMapping);

        *//*classMapper.setTrustedPackages(
                "com.fishstore.shared.dto", // Add this package to the trusted list
                "java.util",
                "java.lang",
                "com.fishtore.transaction.dto"
        );*//*

        converter.setClassMapper(classMapper);
        return converter;
    }*/


}

