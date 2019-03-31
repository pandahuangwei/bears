package com.bear.user.service;

import com.bear.common.entity.Page;
import com.bear.common.entity.user.AppUser;
import com.bear.common.entity.user.LoginAppUser;
import com.bear.common.entity.user.Role;

import java.util.Map;
import java.util.Set;

/**
 * @author panda.huangwei.
 * @since 2018-11-26 1:06.
 */
public interface AppUserService {
    void addAppUser(AppUser appUser);

    void updateAppUser(AppUser appUser);

    LoginAppUser findByUsername(String username);

    AppUser findById(Long id);

    void setRoleToUser(Long id, Set<Long> roleIds);

    void updatePassword(Long id, String oldPassword, String newPassword);

    Page<AppUser> findUsers(Map<String, Object> params);

    Set<Role> findRolesByUserId(Long userId);

    void bindingPhone(Long userId, String phone);
}
