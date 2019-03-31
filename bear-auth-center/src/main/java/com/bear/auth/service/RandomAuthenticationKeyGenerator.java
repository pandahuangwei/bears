package com.bear.auth.service;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;

import java.util.UUID;

/**
 * 解决同一username每次登陆access_token都相同的问题,看下RedisTokenStore的方法getAccessToken便知<br>
 *
 * @author panda.
 * @version 1.0.
 * @see org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator
 * @see org.springframework.security.oauth2.provider.token.TokenStore
 * @since 2018-11-26 14:46.
 */
public class RandomAuthenticationKeyGenerator implements AuthenticationKeyGenerator {

    @Override
    public String extractKey(OAuth2Authentication authentication) {
        return UUID.randomUUID().toString();
    }
}
