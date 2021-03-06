package com.bear.user.mapper;

import com.bear.common.entity.user.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * 用户角色关系<br>
 * 用户和角色是多对多关系，sys_role_user是中间表
 *
 * @author panda.
 * @since 2018-11-26 1:04.
 */
@Mapper
public interface UserRoleMapper {
    /**
     * 删除角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 0  or 1
     */
    int deleteUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 保存用户与角色关系
     *
     * @param userId 用户Id
     * @param roleId 角色ID
     * @return 0  or 1
     */
    @Insert("insert into sys_role_user(userId, roleId) values(#{userId}, #{roleId})")
    int saveUserRoles(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 根据用户id获取角色
     *
     * @param userId 用户ID
     * @return Role
     */
    @Select("select r.* from sys_role_user ru inner join sys_role r on r.id = ru.roleId where ru.userId = #{userId}")
    Set<Role> findRolesByUserId(Long userId);
}
