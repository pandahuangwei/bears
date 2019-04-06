package com.bear.admin.service;

import com.bear.admin.entity.BlackIp;
import com.bear.common.entity.Page;

import java.util.Map;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 15:51.
 */
public interface BlackIpService {
    /**
     * 保存ip黑名单
     *
     * @param ip ip
     */
    void save(BlackIp ip);

    /**
     * 删除黑名单id
     *
     * @param ip ip
     */
    void delete(String ip);

    /**
     * 分页列表
     *
     * @param params params
     * @return page
     */
    Page<BlackIp> findBlackIPs(Map<String, Object> params);

}
