package com.bear.log.mapper;

import com.bear.common.entity.log.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author panda.
 * @since 2018-11-26 0:21.
 */
@Mapper
public interface LogMapper {

    @Insert("insert into t_log(username, module, params, remark, flag, createTime) values(#{username}, #{module}, #{params}, #{remark}, #{flag}, #{createTime})")
    int save(Log log);

    int count(Map<String, Object> params);

    List<Log> findData(Map<String, Object> params);
}
