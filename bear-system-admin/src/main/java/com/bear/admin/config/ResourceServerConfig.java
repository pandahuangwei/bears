package com.bear.admin.config;

import com.bear.common.utils.PermitAllUrl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 16:18.
 */

@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and().authorizeRequests()
                // 放开权限的url
                .antMatchers(PermitAllUrl.permitAllUrl("/backend-anon/**", "/favicon.ico", "/css/**", "/js/**",
                        "/fonts/**", "/layui/**", "/img/**", "/pages/**", "/pages/**/*.html", "/*.html")).permitAll()
                .anyRequest().authenticated().and().httpBasic();

        http.headers().frameOptions().sameOrigin();
    }

}