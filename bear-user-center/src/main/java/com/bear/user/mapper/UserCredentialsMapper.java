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
    @Insert("insert into user_credentials(username, type, userId) values(#{username}, #{type}, #{userId})")
    int save(UserCredential userCredential);

    @Select("select * from user_credentials t where t.username = #{username}")
    UserCredential findByUsername(String username);

    @Select("select u.* from app_user u inner join user_credentials c on c.userId = u.id where c.username = #{username}")
    AppUser findUserByUsername(String username);
}
