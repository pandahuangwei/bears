package com.bear.user.service.impl;

import com.bear.common.constants.CommonConstants;
import com.bear.common.entity.Page;
import com.bear.common.entity.user.Permission;
import com.bear.common.entity.user.Role;
import com.bear.common.utils.PageUtil;
import com.bear.user.mapper.RoleMapper;
import com.bear.user.mapper.RolePermissionMapper;
import com.bear.user.mapper.UserRoleMapper;
import com.bear.user.service.RoleService;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author panda.huangwei.
 * @since 2018-11-26 1:13.
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Transactional
    @Override
    public void save(Role sysRole) {
        Role role = roleMapper.findByCode(sysRole.getCode());
        if (role != null) {
            throw new IllegalArgumentException("角色code已存在");
        }

        sysRole.setCreateTime(new Date());
        sysRole.setUpdateTime(sysRole.getCreateTime());

        roleMapper.save(sysRole);
        log.info("保存角色：{}", sysRole);
    }

    @Transactional
    @Override
    public void update(Role sysRole) {
        sysRole.setUpdateTime(new Date());

        roleMapper.update(sysRole);
        log.info("修改角色：{}", sysRole);
    }

    @Transactional
    @Override
    public void deleteRole(Long id) {
        Role sysRole = roleMapper.findById(id);

        roleMapper.delete(id);
        rolePermissionMapper.deleteRolePermission(id, null);
        userRoleMapper.deleteUserRole(null, id);

        log.info("删除角色：{}", sysRole);

        // 发布role删除的消息
        amqpTemplate.convertAndSend(CommonConstants.MQ_EXCHANGE_USER, CommonConstants.ROUTING_KEY_ROLE_DELETE, id);
    }

    /**
     * 给角色设置权限
     *
     * @param roleId
     * @param permissionIds
     */
    @Transactional
    @Override
    public void setPermissionToRole(Long roleId, Set<Long> permissionIds) {
        Role sysRole = roleMapper.findById(roleId);
        if (sysRole == null) {
            throw new IllegalArgumentException("角色不存在");
        }

        // 查出角色对应的old权限
        Set<Long> oldPermissionIds = rolePermissionMapper.findPermissionsByRoleIds(Sets.newHashSet(roleId)).stream()
                .map(p -> p.getId()).collect(Collectors.toSet());

        // 需要添加的权限
        Collection<Long> addPermissionIds = org.apache.commons.collections4.CollectionUtils.subtract(permissionIds,
                oldPermissionIds);
        if (!CollectionUtils.isEmpty(addPermissionIds)) {
            addPermissionIds.forEach(permissionId -> {
                rolePermissionMapper.saveRolePermission(roleId, permissionId);
            });
        }
        // 需要移除的权限
        Collection<Long> deletePermissionIds = org.apache.commons.collections4.CollectionUtils
                .subtract(oldPermissionIds, permissionIds);
        if (!CollectionUtils.isEmpty(deletePermissionIds)) {
            deletePermissionIds.forEach(permissionId -> {
                rolePermissionMapper.deleteRolePermission(roleId, permissionId);
            });
        }

        log.info("给角色id：{}，分配权限：{}", roleId, permissionIds);
    }

    @Override
    public Role findById(Long id) {
        return roleMapper.findById(id);
    }

    @Override
    public Page<Role> findRoles(Map<String, Object> params) {
        int total = roleMapper.count(params);
        List<Role> list = Collections.emptyList();
        if (total > 0) {
            PageUtil.pageParamConver(params, false);

            list = roleMapper.findData(params);
        }
        return new Page<>(total, list);
    }

    @Override
    public Set<Permission> findPermissionsByRoleId(Long roleId) {
        return rolePermissionMapper.findPermissionsByRoleIds(Sets.newHashSet(roleId));
    }
}
