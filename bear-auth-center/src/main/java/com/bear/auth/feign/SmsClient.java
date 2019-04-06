package com.bear.auth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author panda.
 * @version 1.0.
 * @since 2018-11-26 11:42.
 */
@FeignClient("bear-notification-center")
public interface SmsClient {
    /**
     * 提醒信息
     *
     * @param key    key
     * @param code   code
     * @param delete delete
     * @param second second
     * @return String
     */
    @GetMapping(value = "/notification-anon/internal/phone", params = {"key", "code"})
    String matcheCodeAndGetPhone(@RequestParam("key") String key, @RequestParam("code") String code,
                                 @RequestParam(value = "delete", required = false) Boolean delete,
                                 @RequestParam(value = "second", required = false) Integer second);
}
