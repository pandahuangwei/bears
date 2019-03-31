package com.bear.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-03-14 15:00.
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AuthCenterApp  extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(AuthCenterApp.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
