package com.bear.notification.mapper;

import com.bear.notification.entity.Sms;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author panda.
 * @since 2018-11-26 0:46.
 */
@Mapper
public interface SmsMapper {
    /**
     * 保存
     *
     * @param sms sms
     * @return int
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into t_sms(phone, signName, templateCode, params, day, createTime, updateTime) "
            + "values(#{phone}, #{signName}, #{templateCode}, #{params}, #{day}, #{createTime}, #{updateTime})")
    int save(Sms sms);

    /**
     * 获取短信实体
     *
     * @param id id
     * @return Sms
     */
    @Select("select * from t_sms t where t.id = #{id}")
    Sms findById(Long id);

    /**
     * 更新短信
     *
     * @param sms sms
     * @return int
     */
    int update(Sms sms);

    /**
     * 统计短信
     *
     * @param params params
     * @return int
     */
    int count(Map<String, Object> params);

    /**
     * 获取短信列表
     *
     * @param params params
     * @return Sms
     */
    List<Sms> findData(Map<String, Object> params);
}
