package com.bear.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author panda.huangwei.
 * @since 2018-11-26 1:22.
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class UserCenterApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(UserCenterApp.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
