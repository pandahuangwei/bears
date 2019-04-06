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
    /**
     * 保存日志
     *
     * @param log log
     * @return int
     */
    @Insert("insert into t_log(username, module, params, remark, flag, createTime) values(#{username}, #{module}, #{params}, #{remark}, #{flag}, #{createTime})")
    int save(Log log);

    /**
     * 日志计数
     *
     * @param params params
     * @return int
     */
    int count(Map<String, Object> params);

    /**
     * 获取日志列表
     *
     * @param params params
     * @return list
     */
    List<Log> findData(Map<String, Object> params);
}
