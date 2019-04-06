package com.bear.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author panda.
 * @since 2018-11-25 23:51.
 */
@EnableEurekaServer
@SpringBootApplication
public class RegisterCenterApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(RegisterCenterApp.class, args);
    }

    /**
     * 为了打包springboot项目
     *
     * @param builder builder
     * @return SpringApplicationBuilder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
