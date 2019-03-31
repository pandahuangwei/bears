package com.bear.admin.service.impl;

import com.bear.admin.mapper.MailMapper;
import com.bear.admin.service.MailService;
import com.bear.admin.service.SendMailService;
import com.bear.common.constants.MailStatusEnum;
import com.bear.common.entity.Page;
import com.bear.common.entity.mail.Mail;
import com.bear.common.entity.user.AppUser;
import com.bear.common.utils.AppUserUtil;
import com.bear.common.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 15:54.
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailMapper mailMapper;
    @Autowired
    private SendMailService sendMailService;

    /**
     * 保存邮件
     *
     * @param mail
     */
    @Transactional
    @Override
    public void saveMail(Mail mail) {
        if (mail.getUserId() == null || StringUtils.isBlank(mail.getUsername())) {
            AppUser appUser = AppUserUtil.getLoginAppUser();
            if (appUser != null) {
                mail.setUserId(appUser.getId());
                mail.setUsername(appUser.getUsername());
            }
        }
        if (mail.getUserId() == null) {
            mail.setUserId(0L);
            mail.setUsername("系统邮件");
        }

        if (mail.getCreateTime() == null) {
            mail.setCreateTime(new Date());
        }
        mail.setUpdateTime(mail.getCreateTime());
        mail.setStatus(MailStatusEnum.DRAFT.getCode());

        mailMapper.save(mail);
        log.info("保存邮件：{}", mail);
    }

    /**
     * 修改未发送邮件
     *
     * @param mail
     */
    @Transactional
    @Override
    public void updateMail(Mail mail) {
        Mail oldMail = mailMapper.findById(mail.getId());
        if (MailStatusEnum.isSuccess(oldMail.getStatus())) {
            throw new IllegalArgumentException("已发送的邮件不能编辑");
        }
        mail.setUpdateTime(new Date());

        mailMapper.update(mail);

        log.info("修改邮件：{}", mail);
    }

    /**
     * 异步发送邮件
     *
     * @param mail
     */
    @Override
    @Async
    public void sendMail(Mail mail) {
        boolean flag = sendMailService.sendMail(mail.getToEmail(), mail.getSubject(), mail.getContent());
        mail.setSendTime(new Date());
        // 邮件发送结果
        mail.setStatus(flag ? MailStatusEnum.SUCCESS.getCode() : MailStatusEnum.ERROR.getCode());

        mailMapper.update(mail);
    }

    @Override
    public Mail findById(Long id) {
        return mailMapper.findById(id);
    }

    @Override
    public Page<Mail> findMails(Map<String, Object> params) {
        int total = mailMapper.count(params);
        List<Mail> list = Collections.emptyList();
        if (total > 0) {
            PageUtil.pageParamConver(params, true);

            list = mailMapper.findData(params);
        }
        return new Page<>(total, list);
    }
}
