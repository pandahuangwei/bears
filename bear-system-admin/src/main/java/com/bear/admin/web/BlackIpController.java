package com.bear.admin.web;

import com.bear.admin.entity.BlackIp;
import com.bear.admin.service.BlackIpService;
import com.bear.common.annotation.LogAnnotation;
import com.bear.common.entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 16:20.
 */

@RestController
public class BlackIpController {

    @Autowired
    private BlackIpService blackIPService;

    /**
     * 添加黑名单ip
     *
     */
    @LogAnnotation(module = "添加黑名单")
    @PreAuthorize("hasAuthority('ip:black:save')")
    @PostMapping("/blackIPs")
    public void save(@RequestBody BlackIp blackIP) {
        blackIP.setCreateTime(new Date());

        blackIPService.save(blackIP);
    }

    /**
     * 删除黑名单ip
     *
     */
    @LogAnnotation(module = "删除黑名单")
    @PreAuthorize("hasAuthority('ip:black:delete')")
    @DeleteMapping("/blackIPs")
    public void delete(String ip) {
        blackIPService.delete(ip);
    }

    /**
     * 查询黑名单
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('ip:black:query')")
    @GetMapping("/blackIPs")
    public Page<BlackIp> findBlackIPs(@RequestParam Map<String, Object> params) {
        return blackIPService.findBlackIPs(params);
    }

    /**
     * 查询黑名单<br>
     * 可内网匿名访问
     *
     * @param params
     * @return
     */
    @GetMapping("/backend-anon/internal/blackIPs")
    public Set<String> findAllBlackIPs(@RequestParam Map<String, Object> params) {
        Page<BlackIp> page = blackIPService.findBlackIPs(params);
        if (page.getTotal() > 0) {
            return page.getData().stream().map(BlackIp::getIp).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }
}
