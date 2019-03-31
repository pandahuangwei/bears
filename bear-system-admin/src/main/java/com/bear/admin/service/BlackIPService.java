package com.bear.admin.service;

import com.bear.admin.entity.BlackIP;
import com.bear.common.entity.Page;

import java.util.Map;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 15:51.
 */
public interface BlackIPService {

    void save(BlackIP blackIP);

    void delete(String ip);

    Page<BlackIP> findBlackIPs(Map<String, Object> params);

}
