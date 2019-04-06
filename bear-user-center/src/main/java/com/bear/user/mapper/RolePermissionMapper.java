package com.bear.user.mapper;

import com.bear.common.entity.user.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * 角色权限关系<br>
 * 角色和权限是多对多关系，sys_role_permission是中间表
 *
 * @author panda.
 * @since 2018-11-26 1:01.
 */
@Mapper
public interface RolePermissionMapper {
    /**
     * 保存角色权限
     *
     * @param roleId       角色
     * @param permissionId 权限id
     * @return int
     */
    @Insert("insert into sys_role_permission(roleId, permissionId) values(#{roleId}, #{permissionId})")
    int saveRolePermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    /**
     * 删除角色权限
     *
     * @param roleId       角色
     * @param permissionId 权限
     * @return int
     */
    int deleteRolePermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    /**
     * 获取角色权限
     *
     * @param roleIds 角色ids
     * @return Permission
     */
    Set<Permission> findPermissionsByRoleIds(@Param("roleIds") Set<Long> roleIds);

}
