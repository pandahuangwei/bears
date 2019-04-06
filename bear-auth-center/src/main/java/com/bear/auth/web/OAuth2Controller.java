package com.bear.auth.web;

import com.bear.log.autoconfig.LogMqClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panda.
 * @version 1.0.
 * @since 2018-11-26 14:49.
 */
@Slf4j
@RestController
@RequestMapping
public class OAuth2Controller {

    @Autowired
    private ConsumerTokenServices tokenServices;
    @Autowired
    private LogMqClient logMqClient;

    /**
     * 当前登陆用户信息<br>
     * <p>
     * security获取当前登录用户的方法是SecurityContextHolder.getContext().getAuthentication()<br>
     * 返回值是接口org.springframework.security.core.Authentication，又继承了Principal<br>
     * 这里的实现类是org.springframework.security.oauth2.provider.OAuth2Authentication<br>
     * <p>
     * 因此这只是一种写法，下面注释掉的三个方法也都一样，这四个方法任选其一即可，也只能选一个，毕竟uri相同，否则启动报错<br>
     * 2018.05.23改为默认用这个方法，好理解一点
     *
     * @return
     */
    @GetMapping("/user-me")
    public Authentication principal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("user-me:{}", authentication.getName());
        return authentication;
    }

    /**
     * 注销登陆/退出
     * 移除access_token和refresh_token<br>
     * 2018.06.28 改为用ConsumerTokenServices，该接口的实现类DefaultTokenServices已有相关实现，我们不再重复造轮子
     *
     * @param accessToken token
     */
    @DeleteMapping(value = "/remove_token", params = "access_token")
    public void removeToken(String accessToken) {
        boolean flag = tokenServices.revokeToken(accessToken);
        if (flag) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            saveLogoutLog(authentication.getName());
        }
    }


    /**
     * 退出日志
     *
     * @param username
     */
    private void saveLogoutLog(String username) {
        log.info("{}退出", username);
        // 异步
//        CompletableFuture.runAsync(() -> {
//            try {
//                Log log = Log.builder().username(username).module("退出").createTime(new Date()).build();
//                logClient.save(log);
//            } catch (Exception e) {
//                // do nothing
//            }
//
//        });
        //  调整为mq的方式记录退出日志
        logMqClient.sendLogMsg("退出", username, null, null, true);
    }

}