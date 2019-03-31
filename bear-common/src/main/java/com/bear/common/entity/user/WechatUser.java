package com.bear.common.entity.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author panda.
 * @version 1.0.
 * @since 2018-11-25 16:23.
 */
@Getter
@Setter
@ToString
public class WechatUser implements Serializable {

    private static final long serialVersionUID = 6750304307961875043L;

    private Long id;
    private String openid;
    private String unionid;
    private Long userId;
    private String app;
    private String nickname;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private Date createTime;
    private Date updateTime;
}

