package com.bear.admin.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 15:39.
 */
@Data
public class BlackIp implements Serializable {

    private static final long serialVersionUID = -20L;

    private String ip;
    private Date createTime;
}
