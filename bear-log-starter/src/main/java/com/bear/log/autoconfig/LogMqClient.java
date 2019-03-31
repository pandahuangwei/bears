package com.bear.log.autoconfig;

import com.bear.common.constants.CommonConstants;
import com.bear.common.entity.log.Log;
import com.bear.common.entity.user.LoginAppUser;
import com.bear.common.utils.AppUserUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * 通过mq发送日志<br>
 * 在LogAutoConfig中将该类声明成Bean，用时直接@Autowired即可
 *
 * @author panda.huangwei.
 * @since 2018-11-26 0:36.
 */
public class LogMqClient {
    private static final Logger logger = LoggerFactory.getLogger(LogMqClient.class);

    private AmqpTemplate amqpTemplate;

    public LogMqClient(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendLogMsg(String module, String username, String params, String remark, boolean flag) {
        CompletableFuture.runAsync(() -> {
            try {
                Log log = new Log();
                log.setCreateTime(new Date());
                if (StringUtils.isNotBlank(username)) {
                    log.setUsername(username);
                } else {
                    LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                    if (loginAppUser != null) {
                        log.setUsername(loginAppUser.getUsername());
                    }
                }

                log.setFlag(flag);
                log.setModule(module);
                log.setParams(params);
                log.setRemark(remark);

                amqpTemplate.convertAndSend(CommonConstants.LOG_QUEUE, log);
                logger.info("发送日志到队列：{}", log);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        });
    }
}