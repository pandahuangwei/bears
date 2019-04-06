package com.bear.log.config;

import com.bear.common.constants.CommonConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq配置.
 *
 * @author panda.
 * @since 2018-11-26 0:24.
 */
@Configuration
public class RabbitmqConfig {
    /**
     * 声明队列.
     *
     * @return
     */
    @Bean
    public Queue logQueue() {
        return new Queue(CommonConstants.LOG_QUEUE);
    }
}
