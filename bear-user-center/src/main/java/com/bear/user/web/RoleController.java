package com.bear.user.web;

import com.bear.common.annotation.LogAnnotation;
import com.bear.common.entity.Page;
import com.bear.common.entity.user.Permission;
import com.bear.common.entity.user.Role;
import com.bear.user.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * @author panda.
 * @since 2018-11-26 1:21.
 */
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 管理后台添加角色
     *
     * @param role
     */
    @LogAnnotation(module = "添加角色")
    @PreAuthorize("hasAuthority('back:role:save')")
    @PostMapping("/roles")
    public Role save(@RequestBody Role role) {
        if (StringUtils.isBlank(role.getCode())) {
            throw new IllegalArgumentException("角色code不能为空");
        }
        if (StringUtils.isBlank(role.getName())) {
            role.setName(role.getCode());
        }

        roleService.save(role);

        return role;
    }

    /**
     * 管理后台删除角色
     *
     * @param id
     */
    @LogAnnotation(module = "删除角色")
    @PreAuthorize("hasAuthority('back:role:delete')")
    @DeleteMapping("/roles/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }

    /**
     * 管理后台修改角色
     *
     * @param sysRole
     */
    @LogAnnotation(module = "修改角色")
    @PreAuthorize("hasAuthority('back:role:update')")
    @PutMapping("/roles")
    public Role update(@RequestBody Role sysRole) {
        if (StringUtils.isBlank(sysRole.getName())) {
            throw new IllegalArgumentException("角色名不能为空");
        }

        roleService.update(sysRole);

        return sysRole;
    }

    /**
     * 管理后台给角色分配权限
     *
     * @param id            角色id
     * @param permissionIds 权限ids
     */
    @LogAnnotation(module = "分配权限")
    @PreAuthorize("hasAuthority('back:role:permission:set')")
    @PostMapping("/roles/{id}/permissions")
    public void setPermissionToRole(@PathVariable Long id, @RequestBody Set<Long> permissionIds) {
        roleService.setPermissionToRole(id, permissionIds);
    }

    /**
     * 获取角色的权限
     *
     * @param id
     */
    @PreAuthorize("hasAnyAuthority('back:role:permission:set','role:permission:byroleid')")
    @GetMapping("/roles/{id}/permissions")
    public Set<Permission> findPermissionsByRoleId(@PathVariable Long id) {
        return roleService.findPermissionsByRoleId(id);
    }

    @PreAuthorize("hasAuthority('back:role:query')")
    @GetMapping("/roles/{id}")
    public Role findById(@PathVariable Long id) {
        return roleService.findById(id);
    }

    /**
     * 搜索角色
     *
     * @param params
     */
    @PreAuthorize("hasAuthority('back:role:query')")
    @GetMapping("/roles")
    public Page<Role> findRoles(@RequestParam Map<String, Object> params) {
        return roleService.findRoles(params);
    }

}
