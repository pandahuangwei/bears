package com.bear.admin.config;

import com.bear.common.constants.CommonConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 16:17.
 */
@Configuration
public class RabbitmqConfig {

    /**
     * 角色删除队列名
     */
    public static final String ROLE_DELETE_QUEUE = "role.delete.queue";

    /**
     * 声明队列，此队列用来接收角色删除的消息
     *
     * @return
     */
    @Bean
    public Queue roleDeleteQueue() {
        Queue queue = new Queue(ROLE_DELETE_QUEUE);

        return queue;
    }

    @Bean
    public TopicExchange userTopicExchange() {
        return new TopicExchange(CommonConstants.MQ_EXCHANGE_USER);
    }

    /**
     * 将角色删除队列和用户的exchange做个绑定
     *
     * @return
     */
    @Bean
    public Binding bindingRoleDelete() {
        Binding binding = BindingBuilder.bind(roleDeleteQueue()).to(userTopicExchange())
                .with(CommonConstants.ROUTING_KEY_ROLE_DELETE);
        return binding;
    }
}