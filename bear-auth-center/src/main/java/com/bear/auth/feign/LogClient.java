package com.bear.auth.feign;

import com.bear.common.entity.log.Log;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author panda.
 * @version 1.0.
 * @since 2018-11-26 11:41.
 */
@FeignClient("bear-log-center")
public interface LogClient {

    @PostMapping("/logs-anon/internal")
    void save(@RequestBody Log log);
}
