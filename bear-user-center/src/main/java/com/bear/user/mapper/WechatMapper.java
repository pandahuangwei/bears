package com.bear.user.mapper;

import com.bear.common.entity.user.WechatUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * @author panda.huangwei.
 * @since 2018-11-26 1:05.
 */
@Mapper
public interface WechatMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into t_wechat(openid, unionid, userId, app, nickname, sex, province, city, country, headimgurl, createTime, updateTime) " +
            "values(#{openid}, #{unionid}, #{userId}, #{app}, #{nickname}, #{sex}, #{province}, #{city}, #{country}, #{headimgurl}, #{createTime}, #{updateTime})")
    int save(WechatUser info);

    @Select("select * from t_wechat t where t.openid = #{openid}")
    WechatUser findByOpenid(String openid);

    @Select("select * from t_wechat t where t.unionid = #{unionid}")
    Set<WechatUser> findByUniond(String unionid);

    int update(WechatUser info);
}
