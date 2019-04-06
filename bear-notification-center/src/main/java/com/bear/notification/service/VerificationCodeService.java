package com.bear.notification.service;

import com.bear.notification.entity.VerificationCode;

/**
 * @author panda.
 * @since 2018-11-26 0:47.
 */
public interface VerificationCodeService {
    /**
     * 产生验证码
     *
     * @param phone 手机号
     * @return VerificationCode
     */
    VerificationCode generateCode(String phone);

    /**
     * 获取信息
     *
     * @param key    key
     * @param code   code
     * @param delete delete
     * @param second second
     * @return String
     */
    String matcheCodeAndGetPhone(String key, String code, Boolean delete, Integer second);

}
