package com.bear.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-03-30 18:05.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SystemAdminApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(SystemAdminApp.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
