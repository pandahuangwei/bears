package com.bear.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * @author panda.
 * @version 1.0.
 * @since 2018-11-26 11:16.
 */
@FeignClient("bear-system-admin")
public interface BackendClient {
    /**
     * 获取所有黑名单ip
     *
     * @param params params
     * @return set
     */
    @GetMapping("/backend-anon/internal/blackIPs")
    Set<String> findAllBlackIPs(@RequestParam("params") Map<String, Object> params);
}
