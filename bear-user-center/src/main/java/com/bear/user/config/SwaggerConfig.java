package com.bear.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author panda.
 * @since 2018-11-26 1:00.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("用户中心swagger接口文档")
                .apiInfo(new ApiInfoBuilder().title("用户中心swagger接口文档")
                        .contact(new Contact("panda", "", "191130254@qq.com")).version("1.0").build())
                .select().paths(PathSelectors.any()).build();
    }
}
