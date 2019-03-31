package com.bear.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 监控中心.
 *
 * @author panda.huangwei.
 * @since 2018-11-26 0:39.
 */
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class MonitorApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(MonitorApp.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
