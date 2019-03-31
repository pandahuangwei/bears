package com.bear.notification.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author panda.huangwei.
 * @since 2018-11-26 0:45.
 */
@Data
public class Sms implements Serializable {

    private static final long serialVersionUID = -1L;

    private Long id;
    private String phone;
    private String signName;
    private String templateCode;
    private String params;
    private String bizId;
    private String code;
    private String message;
    private Date day;
    private Date createTime;
    private Date updateTime;
}