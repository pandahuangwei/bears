package com.bear.user.service;

import com.bear.common.entity.Page;
import com.bear.common.entity.user.Permission;
import com.bear.common.entity.user.Role;

import java.util.Map;
import java.util.Set;

/**
 * @author panda.huangwei.
 * @since 2018-11-26 1:07.
 */
public interface RoleService {
    void save(Role sysRole);

    void update(Role sysRole);

    void deleteRole(Long id);

    void setPermissionToRole(Long id, Set<Long> permissionIds);

    Role findById(Long id);

    Page<Role> findRoles(Map<String, Object> params);

    Set<Permission> findPermissionsByRoleId(Long roleId);
}
