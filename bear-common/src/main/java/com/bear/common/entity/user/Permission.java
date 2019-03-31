package com.bear.common.entity.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author panda.
 * @version 1.0.
 * @since 2018-11-25 16:24.
 */
@Data
public class Permission implements Serializable {

    private static final long serialVersionUID = 280565233032255804L;

    private Long id;
    private String permission;
    private String name;
    private Date createTime;
    private Date updateTime;

}
