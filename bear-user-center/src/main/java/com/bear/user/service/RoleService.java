package com.bear.user.service;

import com.bear.common.entity.Page;
import com.bear.common.entity.user.Permission;
import com.bear.common.entity.user.Role;

import java.util.Map;
import java.util.Set;

/**
 * @author panda.
 * @since 2018-11-26 1:07.
 */
public interface RoleService {
    /**
     * 保存角色信息
     *
     * @param role role
     */
    void save(Role role);

    /**
     * 更新角色信息
     *
     * @param role role
     */
    void update(Role role);

    /**
     * 删除角色
     *
     * @param id id
     */
    void deleteRole(Long id);

    /**
     * 权限
     *
     * @param id            id
     * @param permissionIds ids
     */
    void setPermissionToRole(Long id, Set<Long> permissionIds);

    /**
     * 获取角色
     *
     * @param id id
     * @return Role
     */
    Role findById(Long id);

    /**
     * 分页获取角色
     *
     * @param params params
     * @return Role
     */
    Page<Role> findRoles(Map<String, Object> params);

    /**
     * 获取权限
     *
     * @param roleId roleId
     * @return Permission
     */
    Set<Permission> findPermissionsByRoleId(Long roleId);
}
