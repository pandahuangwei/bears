package com.bear.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author panda.huangwei.
 * @since 2018-11-26 0:53.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class NotificationCenterApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(NotificationCenterApp.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
