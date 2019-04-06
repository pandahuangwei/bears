package com.bear.user.service;

import com.bear.common.entity.Page;
import com.bear.common.entity.user.AppUser;
import com.bear.common.entity.user.LoginAppUser;
import com.bear.common.entity.user.Role;

import java.util.Map;
import java.util.Set;

/**
 * @author panda.
 * @since 2018-11-26 1:06.
 */
public interface AppUserService {
    /**
     * 新增用户
     *
     * @param appUser user
     */
    void addAppUser(AppUser appUser);

    /**
     * 更新用户
     *
     * @param appUser appUser
     */
    void updateAppUser(AppUser appUser);

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 登录用户信息
     */
    LoginAppUser findByUsername(String username);

    /**
     * 获取用户
     *
     * @param id id
     * @return 用户
     */
    AppUser findById(Long id);

    /**
     * 给用户授予角色
     *
     * @param id      id
     * @param roleIds role
     */
    void setRoleToUser(Long id, Set<Long> roleIds);

    /**
     * 更新密码
     *
     * @param id          id
     * @param oldPassword pwd
     * @param newPassword pwd
     */
    void updatePassword(Long id, String oldPassword, String newPassword);

    /**
     * 获取用户
     *
     * @param params param
     * @return page
     */
    Page<AppUser> findUsers(Map<String, Object> params);

    /**
     * 获取角色
     *
     * @param userId 用户
     * @return role
     */
    Set<Role> findRolesByUserId(Long userId);

    /**
     * 绑定手机号
     *
     * @param userId 用户
     * @param phone  手机
     */
    void bindingPhone(Long userId, String phone);
}
