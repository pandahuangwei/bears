package com.bear.notification.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.bear.common.entity.Page;
import com.bear.notification.entity.Sms;

import java.util.Map;

/**
 * @author panda.huangwei.
 * @since 2018-11-26 0:47.
 */
public interface SmsService {
    void save(Sms sms, Map<String, String> params);

    void update(Sms sms);

    Sms findById(Long id);

    Page<Sms> findSms(Map<String, Object> params);

    /**
     * 发送短信
     */
    SendSmsResponse sendSmsMsg(Sms sms);
}
