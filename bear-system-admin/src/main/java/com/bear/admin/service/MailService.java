package com.bear.admin.service;

import com.bear.common.entity.Page;
import com.bear.common.entity.mail.Mail;

import java.util.Map;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 15:51.
 */
public interface MailService {
    /**
     * 保存mail
     *
     * @param mail mail
     */
    void saveMail(Mail mail);

    /**
     * 更新mail
     *
     * @param mail mail
     */
    void updateMail(Mail mail);

    /**
     * 发送mail
     *
     * @param mail mail
     */
    void sendMail(Mail mail);

    /**
     * 获取mail
     *
     * @param id id
     * @return mail
     */
    Mail findById(Long id);

    /**
     * 分页获取mail
     *
     * @param params param
     * @return page
     */
    Page<Mail> findMails(Map<String, Object> params);
}