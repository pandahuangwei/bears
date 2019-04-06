package com.bear.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author panda.
 * @since 2018-11-26 1:17.
 */
@FeignClient("bear-notification-center")
public interface SmsClient {

    @GetMapping(value = "/notification-anon/internal/phone", params = { "key", "code" })
    public String matcheCodeAndGetPhone(@RequestParam("key") String key, @RequestParam("code") String code,
                                        @RequestParam(value = "delete", required = false) Boolean delete,
                                        @RequestParam(value = "second", required = false) Integer second);
}
