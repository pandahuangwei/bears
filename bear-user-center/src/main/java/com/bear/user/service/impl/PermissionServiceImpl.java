package com.bear.user.service.impl;

import com.bear.common.entity.Page;
import com.bear.common.entity.user.Permission;
import com.bear.common.utils.PageUtil;
import com.bear.user.mapper.PermissionMapper;
import com.bear.user.mapper.RolePermissionMapper;
import com.bear.user.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author panda.huangwei.
 * @since 2018-11-26 1:12.
 */
@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public Set<Permission> findByRoleIds(Set<Long> roleIds) {
        return rolePermissionMapper.findPermissionsByRoleIds(roleIds);
    }

    @Transactional
    @Override
    public void save(Permission sysPermission) {
        Permission permission = permissionMapper.findByPermission(sysPermission.getPermission());
        if (permission != null) {
            throw new IllegalArgumentException("权限标识已存在");
        }
        sysPermission.setCreateTime(new Date());
        sysPermission.setUpdateTime(sysPermission.getCreateTime());

        permissionMapper.save(sysPermission);
        log.info("保存权限标识：{}", sysPermission);
    }

    @Transactional
    @Override
    public void update(Permission sysPermission) {
        sysPermission.setUpdateTime(new Date());
        permissionMapper.update(sysPermission);
        log.info("修改权限标识：{}", sysPermission);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Permission permission = permissionMapper.findById(id);
        if (permission == null) {
            throw new IllegalArgumentException("权限标识不存在");
        }

        permissionMapper.delete(id);
        rolePermissionMapper.deleteRolePermission(null, id);
        log.info("删除权限标识：{}", permission);
    }

    @Override
    public Page<Permission> findPermissions(Map<String, Object> params) {
        int total = permissionMapper.count(params);
        List<Permission> list = Collections.emptyList();
        if (total > 0) {
            PageUtil.pageParamConver(params, false);

            list = permissionMapper.findData(params);
        }
        return new Page<>(total, list);
    }
}
