package com.bear.user.config;

import com.bear.common.constants.CommonConstants;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author panda.
 * @since 2018-11-26 0:58.
 */
@Configuration
public class RabbitmqConfig {

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(CommonConstants.MQ_EXCHANGE_USER);
    }
}

