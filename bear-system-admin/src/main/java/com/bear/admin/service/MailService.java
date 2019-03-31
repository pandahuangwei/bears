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

    void saveMail(Mail mail);

    void updateMail(Mail mail);

    void sendMail(Mail mail);

    Mail findById(Long id);

    Page<Mail> findMails(Map<String, Object> params);
}