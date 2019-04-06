package com.bear.auth.feign;

import com.bear.common.entity.user.LoginAppUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author panda.
 * @version 1.0.
 * @since 2018-11-26 11:42.
 */
@FeignClient("bear-user-center")
public interface UserClient {
    /**
     * 根据用户名获取登录用户信息
     *
     * @param username username
     * @return LoginAppUser
     */
    @GetMapping(value = "/users-anon/internal", params = "username")
    LoginAppUser findByUsername(@RequestParam("username") String username);

    /**
     * 登录校验
     *
     * @param tempCode tempCode
     * @param openid   openid
     */
    @GetMapping("/wechat/login-check")
    void wechatLoginCheck(@RequestParam("tempCode") String tempCode, @RequestParam("openid") String openid);
}
