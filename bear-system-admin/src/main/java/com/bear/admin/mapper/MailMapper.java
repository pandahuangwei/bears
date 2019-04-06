package com.bear.admin.mapper;

import com.bear.common.entity.mail.Mail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 15:44.
 */
@Mapper
public interface MailMapper {
    /**
     * 保存mail
     *
     * @param mail mail
     * @return int
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into t_mail(userId, username, toEmail, subject, content, status, createTime, updateTime) values(#{userId}, #{username}, #{toEmail}, #{subject}, #{content}, #{status}, #{createTime}, #{updateTime})")
    int save(Mail mail);

    /**
     * 更新mail
     *
     * @param mail mail
     * @return int
     */
    int update(Mail mail);

    /**
     * 获取mail
     *
     * @param id id
     * @return mail
     */
    @Select("select * from t_mail t where t.id = #{id}")
    Mail findById(Long id);

    /**
     * 计数mail
     *
     * @param params param
     * @return int
     */
    int count(Map<String, Object> params);

    /**
     * 获取列表mail
     *
     * @param params param
     * @return mail
     */
    List<Mail> findData(Map<String, Object> params);
}