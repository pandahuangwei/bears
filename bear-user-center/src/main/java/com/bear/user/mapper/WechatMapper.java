package com.bear.user.mapper;

import com.bear.common.entity.user.WechatUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * @author panda.
 * @since 2018-11-26 1:05.
 */
@Mapper
public interface WechatMapper {
    /**
     * 保存用户信息
     *
     * @param info user
     * @return 0 or 1
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into t_wechat(openid, unionid, userId, app, nickname, sex, province, city, country, headimgurl, createTime, updateTime) " +
            "values(#{openid}, #{unionid}, #{userId}, #{app}, #{nickname}, #{sex}, #{province}, #{city}, #{country}, #{headimgurl}, #{createTime}, #{updateTime})")
    int save(WechatUser info);

    /**
     * 获取用户信息
     *
     * @param openid id
     * @return WechatUser
     */
    @Select("select * from t_wechat t where t.openid = #{openid}")
    WechatUser findByOpenid(String openid);

    /**
     * 获取用户信息
     *
     * @param unionid id
     * @return WechatUser
     */
    @Select("select * from t_wechat t where t.unionid = #{unionid}")
    Set<WechatUser> findByUniond(String unionid);

    /**
     * 更新用户信息.
     *
     * @param info user
     * @return 0 or 1
     */
    int update(WechatUser info);
}
