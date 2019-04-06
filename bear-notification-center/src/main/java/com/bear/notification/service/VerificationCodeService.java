package com.bear.notification.service;

import com.bear.notification.entity.VerificationCode;

/**
 * @author panda.
 * @since 2018-11-26 0:47.
 */
public interface VerificationCodeService {
    VerificationCode generateCode(String phone);

    String matcheCodeAndGetPhone(String key, String code, Boolean delete, Integer second);

}
