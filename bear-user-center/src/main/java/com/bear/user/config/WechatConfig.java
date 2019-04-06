package com.bear.user.config;

import com.bear.common.entity.user.WechatInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author panda.
 * @since 2018-11-26 1:00.
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "wechat")
public class WechatConfig {

    private String domain;
    private Map<String, WechatInfo> infos;

}