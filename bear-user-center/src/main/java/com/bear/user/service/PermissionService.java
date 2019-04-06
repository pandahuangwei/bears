package com.bear.user.service;

import com.bear.common.entity.Page;
import com.bear.common.entity.user.Permission;

import java.util.Map;
import java.util.Set;

/**
 * @author panda.
 * @since 2018-11-26 1:06.
 */
public interface PermissionService {
    /**
     * 根绝角色ids获取权限集合
     *
     * @param roleIds
     * @return
     */
    Set<Permission> findByRoleIds(Set<Long> roleIds);

    void save(Permission sysPermission);

    void update(Permission sysPermission);

    void delete(Long id);

    Page<Permission> findPermissions(Map<String, Object> params);
}
