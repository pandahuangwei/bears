package com.bear.admin.mapper;

import com.bear.admin.entity.BlackIP;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 15:40.
 */
@Mapper
public interface BlackIPMapper {

    @Insert("insert into black_ip(ip, createTime) values(#{ip}, #{createTime})")
    int save(BlackIP blackIP);

    @Delete("delete from black_ip where ip = #{ip}")
    int delete(String ip);

    @Select("select * from black_ip t where t.ip = #{ip}")
    BlackIP findByIp(String ip);

    int count(Map<String, Object> params);

    List<BlackIP> findData(Map<String, Object> params);
}