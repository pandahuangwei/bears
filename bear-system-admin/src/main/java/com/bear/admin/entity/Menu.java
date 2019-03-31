package com.bear.admin.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 15:39.
 */
@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = 7L;

    private Long id;
    private Long parentId;
    private String name;
    private String css;
    private String url;
    private Integer sort;
    private Date createTime;
    private Date updateTime;

    private List<Menu> child;
}