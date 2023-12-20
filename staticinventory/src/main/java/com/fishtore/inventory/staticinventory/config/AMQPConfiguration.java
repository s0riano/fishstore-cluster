package com.fishtore.inventory.staticinventory.config;


import com.fishtore.inventory.staticinventory.dto.TransactionRequestDTO;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.MessageConverter; // Import for MessageConverter
import java.util.HashMap;
import java.util.Map;// Import for Map


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

        // Define the mapping between the typeId header and the expected type
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("TransactionMessagePayload", TransactionRequestDTO.class); // Adjust the mapping as needed
        classMapper.setIdClassMapping(idClassMapping);

        // Set trusted packages
        classMapper.setTrustedPackages(
                "com.fishstore.shared.dto", // Add this package to the trusted list
                "java.util",
                "java.lang",
                "com.fishtore.transaction.dto"
        );

        converter.setClassMapper(classMapper);
        return converter;
    }


}

