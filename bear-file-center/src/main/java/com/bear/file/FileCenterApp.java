package com.bear.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author panda.
 * @version 1.0.
 * @since 2018-11-26 15:17.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class FileCenterApp  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FileCenterApp.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
