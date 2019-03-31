package com.bear.common.entity.mail;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author panda.
 * @since 2018-11-25
 */
@Data
public class Mail implements Serializable {

    private static final long serialVersionUID = 4885093464493499448L;

    private Long id;
    private Long userId;
    /**
     * 发送人用户名
     */
    private String username;
    /**
     * 收件人邮件地址
     */
    private String toEmail;
    /**
     * 标题
     */
    private String subject;
    /**
     * 正文
     */
    private String content;
    /**
     * 发送状态
     *
     * @see com.bear.common.constants.MailStatusEnum
     */
    private Integer status;
    /**
     * 发送时间
     */
    private Date sendTime;
    private Date createTime;
    private Date updateTime;

}
