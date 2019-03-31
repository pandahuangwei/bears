package com.bear.common.entity.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 微信appid和secret对象
 * @author panda.
 * @version 1.0.
 * @since 2018-11-25 16:22.
 */
@Getter
@Setter
@ToString
public class WechatInfo implements Serializable {

    private static final long serialVersionUID = 3511834512371404079L;

    private String appid;
    private String secret;
}
