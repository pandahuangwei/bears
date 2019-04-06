package com.bear.user.mapper;

import com.bear.common.entity.user.AppUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author panda.
 * @since 2018-11-26 1:01.
 */
@Mapper
public interface AppUserMapper {
    /**
     * 保存用户信息
     *
     * @param appUser user
     * @return int
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into app_user(username, password, nickname, headImgUrl, phone, sex, enabled, type, createTime, updateTime) "
            + "values(#{username}, #{password}, #{nickname}, #{headImgUrl}, #{phone}, #{sex}, #{enabled}, #{type}, #{createTime}, #{updateTime})")
    int save(AppUser appUser);

    /**
     * 更新用户信息
     *
     * @param appUser user
     * @return int
     */
    int update(AppUser appUser);

    /**
     * 根据用户名获取
     *
     * @param username username
     * @return user
     */
    @Deprecated
    @Select("select * from app_user t where t.username = #{username}")
    AppUser findByUsername(String username);

    /**
     * 获取用户
     *
     * @param id id
     * @return 用户
     */
    @Select("select * from app_user t where t.id = #{id}")
    AppUser findById(Long id);

    /**
     * 计数
     *
     * @param params param
     * @return int
     */
    int count(Map<String, Object> params);

    /**
     * 获取用户
     *
     * @param params params
     * @return list
     */
    List<AppUser> findData(Map<String, Object> params);
}
