package com.bear.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 16:16.
 */
@EnableAsync(proxyTargetClass = true)
@Configuration
public class AsycTaskExecutorConfig {

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(50);
        taskExecutor.setMaxPoolSize(100);

        return taskExecutor;
    }
}