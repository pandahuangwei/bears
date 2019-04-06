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
     * @param roleIds roleIds
     * @return Permission
     */
    Set<Permission> findByRoleIds(Set<Long> roleIds);

    /**
     * 保存权限
     *
     * @param permission permission
     */
    void save(Permission permission);

    /**
     * 更新权限
     *
     * @param permission permission
     */
    void update(Permission permission);

    /**
     * 删除权限
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 分页获取权限
     *
     * @param params params
     * @return Permission
     */
    Page<Permission> findPermissions(Map<String, Object> params);
}
