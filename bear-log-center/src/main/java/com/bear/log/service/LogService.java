package com.bear.log.service;

import com.bear.common.entity.Page;
import com.bear.common.entity.log.Log;

import java.util.Map;

/**
 * @author panda.
 * @since 2018-11-26 0:18.
 */
public interface LogService {
    /**
     * 保存日志
     *
     * @param log
     */
    void save(Log log);

    /**
     * 分页获取日志
     *
     * @param params param
     * @return page
     */
    Page<Log> findLogs(Map<String, Object> params);
}
