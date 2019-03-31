package com.bear.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象.
 *
 * @author panda.
 * @since 2018-11-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> implements Serializable {
    private static final long serialVersionUID = -1L;

    private int total;
    private List<T> data;
}
