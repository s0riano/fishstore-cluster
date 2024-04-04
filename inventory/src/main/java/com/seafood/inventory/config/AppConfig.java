package com.seafood.inventory.config;

import com.seafood.inventory.components.UUIDGeneratorComponent;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration //centralized configuration class
@EnableTransactionManagement
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public UUIDGeneratorComponent uuidGeneratorComponent() {
        return new UUIDGeneratorComponent();
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory databaseFactory, MongoConverter converter) {
        return new MongoTemplate(databaseFactory, converter);
    }

    @Bean //MessagingConfig
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    } //moved to AMQPConfigurator
}
