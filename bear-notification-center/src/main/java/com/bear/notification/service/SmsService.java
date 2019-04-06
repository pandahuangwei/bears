package com.bear.notification.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.bear.common.entity.Page;
import com.bear.notification.entity.Sms;

import java.util.Map;

/**
 * @author panda.
 * @since 2018-11-26 0:47.
 */
public interface SmsService {
    /**
     * 保存短信
     *
     * @param sms    sms
     * @param params params
     */
    void save(Sms sms, Map<String, String> params);

    /**
     * 更新短信
     *
     * @param sms sms
     */
    void update(Sms sms);

    /**
     * 获取短信
     *
     * @param id id
     * @return Sms
     */
    Sms findById(Long id);

    /**
     * 分页获取短信信息
     *
     * @param params params
     * @return Sms
     */
    Page<Sms> findSms(Map<String, Object> params);

    /**
     * 发送短信
     *
     * @param sms sms
     * @return SendSmsResponse
     */
    SendSmsResponse sendSmsMsg(Sms sms);
}
