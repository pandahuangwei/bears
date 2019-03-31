package com.bear.log.service.impl;

import com.bear.common.entity.Page;
import com.bear.common.entity.log.Log;
import com.bear.common.utils.PageUtil;
import com.bear.log.mapper.LogMapper;
import com.bear.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author panda.huangwei.
 * @since 2018-11-26 0:22.
 */
//@Primary
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    /**
     * 将日志保存到数据库<br>
     * 注解@Async是开启异步执行
     *
     * @param log
     */
    @Async
    @Override
    public void save(Log log) {
        if (log.getCreateTime() == null) {
            log.setCreateTime(new Date());
        }
        if (log.getFlag() == null) {
            log.setFlag(Boolean.TRUE);
        }

        logMapper.save(log);
    }

    @Override
    public Page<Log> findLogs(Map<String, Object> params) {
        int total = logMapper.count(params);
        List<Log> list = Collections.emptyList();
        if (total > 0) {
            PageUtil.pageParamConver(params, true);

            list = logMapper.findData(params);
        }
        return new Page<>(total, list);
    }
}
