package com.bear.admin.service.impl;

import com.bear.admin.entity.BlackIp;
import com.bear.admin.mapper.BlackIpMapper;
import com.bear.admin.service.BlackIpService;
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
public class BlackIpServiceImpl implements BlackIpService {

    @Autowired
    private BlackIpMapper blackIPMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(BlackIp blackIP) {
        BlackIp ip = blackIPMapper.findByIp(blackIP.getIp());
        if (ip != null) {
            throw new IllegalArgumentException(blackIP.getIp() + "已存在");
        }

        blackIPMapper.save(blackIP);
        log.info("添加黑名单ip:{}", blackIP);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String ip) {
        int n = blackIPMapper.delete(ip);
        if (n > 0) {
            log.info("删除黑名单ip:{}", ip);
        }
    }

    @Override
    public Page<BlackIp> findBlackIPs(Map<String, Object> params) {
        int total = blackIPMapper.count(params);
        List<BlackIp> list = Collections.emptyList();
        if (total > 0) {
            PageUtil.pageParamConver(params, false);

            list = blackIPMapper.findData(params);
        }
        return new Page<>(total, list);
    }
}