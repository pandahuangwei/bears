package com.bear.notification.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author panda.huangwei.
 * @since 2018-11-26 0:45.
 */
@Data
public class VerificationCode implements Serializable {

    private static final long serialVersionUID = -3L;

    private String key;
}
