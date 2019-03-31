package com.bear.admin.service.impl;

import com.bear.admin.entity.BlackIP;
import com.bear.admin.mapper.BlackIPMapper;
import com.bear.admin.service.BlackIPService;
import com.bear.common.entity.Page;
import com.bear.common.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 15:53.
 */
@Slf4j
@Service
public class BlackIPServiceImpl implements BlackIPService {

    @Autowired
    private BlackIPMapper blackIPMapper;

    @Transactional
    @Override
    public void save(BlackIP blackIP) {
        BlackIP ip = blackIPMapper.findByIp(blackIP.getIp());
        if (ip != null) {
            throw new IllegalArgumentException(blackIP.getIp() + "已存在");
        }

        blackIPMapper.save(blackIP);
        log.info("添加黑名单ip:{}", blackIP);
    }

    @Transactional
    @Override
    public void delete(String ip) {
        int n = blackIPMapper.delete(ip);
        if (n > 0) {
            log.info("删除黑名单ip:{}", ip);
        }
    }

    @Override
    public Page<BlackIP> findBlackIPs(Map<String, Object> params) {
        int total = blackIPMapper.count(params);
        List<BlackIP> list = Collections.emptyList();
        if (total > 0) {
            PageUtil.pageParamConver(params, false);

            list = blackIPMapper.findData(params);
        }
        return new Page<>(total, list);
    }
}