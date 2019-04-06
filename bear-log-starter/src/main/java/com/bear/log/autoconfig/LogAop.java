package com.bear.log.autoconfig;

import com.alibaba.fastjson.JSONObject;
import com.bear.common.annotation.LogAnnotation;
import com.bear.common.constants.CommonConstants;
import com.bear.common.entity.log.Log;
import com.bear.common.entity.user.LoginAppUser;
import com.bear.common.utils.AppUserUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * aop实现日志
 *
 * @author panda.
 * @since 2018-11-26 0:33.
 */
@Aspect
public class LogAop {
    private static final Logger logger = LoggerFactory.getLogger(LogAop.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 环绕带注解 @LogAnnotation的方法做aop
     */
    @Around(value = "@annotation(com.bear.common.annotation.LogAnnotation)")
    public Object logSave(ProceedingJoinPoint joinPoint) throws Throwable {
        Log log = new Log();
        log.setCreateTime(new Date());
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (loginAppUser != null) {
            log.setUsername(loginAppUser.getUsername());
        }

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
        log.setModule(logAnnotation.module());
        // 是否要记录方法的参数数据
        if (logAnnotation.recordParam()) {
            String[] paramNames = methodSignature.getParameterNames();
            if (paramNames != null && paramNames.length > 0) {
                Object[] args = joinPoint.getArgs();

                Map<String, Object> params = new HashMap<>(8);
                for (int i = 0; i < paramNames.length; i++) {
                    Object value = args[i];
                    if (value instanceof Serializable) {
                        params.put(paramNames[i], value);
                    }
                }

                try {
                    // 以json的形式记录参数
                    log.setParams(JSONObject.toJSONString(params));
                } catch (Exception e) {
                    logger.error("记录参数失败：{}", e.getMessage());
                }
            }
        }

        try {
            // 执行原方法
            Object object = joinPoint.proceed();
            log.setFlag(Boolean.TRUE);

            return object;
        } catch (Exception e) {
            // 方法执行失败
            log.setFlag(Boolean.FALSE);
            // 备注记录失败原因
            log.setRemark(e.getMessage());
            throw e;
        } finally {
            // 异步将Log对象发送到队列
            CompletableFuture.runAsync(() -> {
                try {
                    amqpTemplate.convertAndSend(CommonConstants.LOG_QUEUE, log);
                    logger.info("发送日志到队列：{}", log);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            });

        }

    }
}
