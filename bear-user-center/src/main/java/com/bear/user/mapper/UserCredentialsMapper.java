package com.bear.user.mapper;

import com.bear.common.entity.user.AppUser;
import com.bear.common.entity.user.UserCredential;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author panda.
 * @since 2018-11-26 1:04.
 */
@Mapper
public interface UserCredentialsMapper {
    /**
     * 保存信息
     *
     * @param userCredential user
     * @return 0  or 1
     */
    @Insert("insert into user_credentials(username, type, userId) values(#{username}, #{type}, #{userId})")
    int save(UserCredential userCredential);

    /**
     * 获取信息
     *
     * @param username username
     * @return UserCredential
     */
    @Select("select * from user_credentials t where t.username = #{username}")
    UserCredential findByUsername(String username);


    /**
     * 获取信息
     *
     * @param username username
     * @return AppUser
     */
    @Select("select u.* from app_user u inner join user_credentials c on c.userId = u.id where c.username = #{username}")
    AppUser findUserByUsername(String username);
}
