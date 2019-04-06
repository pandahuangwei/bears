package com.bear.log.consumer;

import com.bear.common.constants.CommonConstants;
import com.bear.common.entity.log.Log;
import com.bear.log.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消费日志。
 * @author panda.
 * @since 2018-11-26 0:27.
 */
@Component
@RabbitListener(queues = CommonConstants.LOG_QUEUE)
public class LogConsumer {
    private static final Logger logger = LoggerFactory.getLogger(LogConsumer.class);

    @Autowired
    private LogService logService;

    /**
     * 处理消息
     *
     * @param log
     */
    @RabbitHandler
    public void logHandler(Log log) {
        try {
            logService.save(log);
        } catch (Exception e) {
            logger.error("保存日志失败，日志：{}，异常：{}", log, e);
        }
    }
}
