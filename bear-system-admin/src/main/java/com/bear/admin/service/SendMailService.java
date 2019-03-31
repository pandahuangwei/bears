package com.bear.admin.service;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 15:52.
 */
public interface SendMailService {

    /**
     * 发送邮件
     *
     * @param toUser
     * @param subject 标题
     * @param text    内容（支持html格式）
     */
    boolean sendMail(String toUser, String subject, String text);
}
