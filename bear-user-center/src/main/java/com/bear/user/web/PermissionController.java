package com.bear.user.web;

import com.bear.common.annotation.LogAnnotation;
import com.bear.common.entity.Page;
import com.bear.common.entity.user.Permission;
import com.bear.user.service.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author panda.huangwei.
 * @since 2018-11-26 1:20.
 */
@RestController
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 管理后台添加权限
     *
     * @param permission
     * @return
     */
    @LogAnnotation(module = "添加权限")
    @PreAuthorize("hasAuthority('back:permission:save')")
    @PostMapping("/permissions")
    public Permission save(@RequestBody Permission permission) {
        if (StringUtils.isBlank(permission.getPermission())) {
            throw new IllegalArgumentException("权限标识不能为空");
        }
        if (StringUtils.isBlank(permission.getName())) {
            throw new IllegalArgumentException("权限名不能为空");
        }

        permissionService.save(permission);

        return permission;
    }

    /**
     * 管理后台修改权限
     *
     * @param permission
     */
    @LogAnnotation(module = "修改权限")
    @PreAuthorize("hasAuthority('back:permission:update')")
    @PutMapping("/permissions")
    public Permission update(@RequestBody Permission permission) {
        if (StringUtils.isBlank(permission.getName())) {
            throw new IllegalArgumentException("权限名不能为空");
        }

        permissionService.update(permission);

        return permission;
    }

    /**
     * 删除权限标识
     *
     * @param id
     */
    @LogAnnotation(module = "删除权限")
    @PreAuthorize("hasAuthority('back:permission:delete')")
    @DeleteMapping("/permissions/{id}")
    public void delete(@PathVariable Long id) {
        permissionService.delete(id);
    }

    /**
     * 查询所有的权限标识
     */
    @PreAuthorize("hasAuthority('back:permission:query')")
    @GetMapping("/permissions")
    public Page<Permission> findPermissions(@RequestParam Map<String, Object> params) {
        return permissionService.findPermissions(params);
    }
}
