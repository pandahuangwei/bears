package com.bear.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 配置中心
 *
 * @author panda.
 * @version 1.0.
 * @since 2018-11-25 17:56.
 */
@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class ConfigCenterApp  extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenterApp.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
