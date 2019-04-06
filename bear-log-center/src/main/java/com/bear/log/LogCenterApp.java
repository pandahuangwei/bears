package com.bear.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 日志中心.
 *
 * @author panda.
 * @since 2018-11-26 0:30.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class LogCenterApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(LogCenterApp.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
