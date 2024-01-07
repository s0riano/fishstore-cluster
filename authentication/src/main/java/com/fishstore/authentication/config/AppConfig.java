package com.fishstore.authentication.config;

import com.fishstore.authentication.components.UUIDGeneratorComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

   /* @Bean //MessagingConfig
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }*/ //moved to AMQPConfigurator
}
