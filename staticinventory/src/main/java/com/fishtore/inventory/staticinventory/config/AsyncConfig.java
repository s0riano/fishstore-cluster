package com.fishtore.inventory.staticinventory.config;

import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
    // Other configurations if necessary

    private ThreadPoolTaskExecutor executor;

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize();
        return executor;
    }

    @PreDestroy
    public void destroy() {
        if (executor != null) {
            executor.shutdown();
        }
    }

}
