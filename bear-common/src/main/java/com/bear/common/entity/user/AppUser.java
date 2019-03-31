package com.bear.common.entity.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author panda.
 * @version 1.0.
 * @since 2018-11-25 15:24.
 */
@Data
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String headImgUrl;
    private String phone;
    private Integer sex;
    /**
     * 状态
     */
    private Boolean enabled;
    private String type;
    private Date createTime;
    private Date updateTime;

}