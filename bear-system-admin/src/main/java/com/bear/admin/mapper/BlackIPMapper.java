package com.bear.admin.mapper;

import com.bear.admin.entity.BlackIP;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 15:40.
 */
@Mapper
public interface BlackIPMapper {
    /**
     * 保存黑名单ip
     *
     * @param ip ip
     * @return int
     */
    @Insert("insert into black_ip(ip, createTime) values(#{ip}, #{createTime})")
    int save(BlackIP ip);

    /**
     * 删除ip
     *
     * @param ip ip
     * @return int
     */
    @Delete("delete from black_ip where ip = #{ip}")
    int delete(String ip);

    /**
     * 获取黑名单ip信息
     *
     * @param ip ip
     * @return Ip
     */
    @Select("select * from black_ip t where t.ip = #{ip}")
    BlackIP findByIp(String ip);

    /**
     * 计数
     *
     * @param params param
     * @return int
     */
    int count(Map<String, Object> params);

    /**
     * 获取列表
     *
     * @param params param
     * @return list
     */
    List<BlackIP> findData(Map<String, Object> params);
}