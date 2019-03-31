package com.bear.common.constants;

/**
 * @author panda.
 * @since 2018-11-25
 */
public interface CommonConstants {
    /**
     * client info
     */
    String CLIENT_ID = "system";
    String CLIENT_SECRET = "system";
    String CLIENT_SCOPE = "app";

    /**
     * 用户系统exchange名
     */
    String MQ_EXCHANGE_USER = "user.topic.exchange";

    /**
     * 角色删除routing key
     */
    String ROUTING_KEY_ROLE_DELETE = "role.delete";

    /**
     * 接收日志的队列名
     */
    String LOG_QUEUE = "system.log.queue";
}
