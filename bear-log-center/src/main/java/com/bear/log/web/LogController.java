package com.bear.log.web;

import com.bear.common.entity.Page;
import com.bear.common.entity.log.Log;
import com.bear.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author panda.
 * @since 2018-11-26 0:29.
 */
@RestController
public class LogController {
    @Autowired
    private LogService logService;

    @PostMapping("/logs-anon/internal")
    public void save(@RequestBody Log log) {
        logService.save(log);
    }

    /**
     * 日志查询
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('log:query')")
    @GetMapping("/logs")
    public Page<Log> findLogs(@RequestParam Map<String, Object> params) {
        return logService.findLogs(params);
    }
}
