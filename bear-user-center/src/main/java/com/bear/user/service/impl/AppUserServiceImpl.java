package com.bear.user.service.impl;

import com.bear.common.constants.CredentialType;
import com.bear.common.constants.UserType;
import com.bear.common.entity.Page;
import com.bear.common.entity.user.*;
import com.bear.common.utils.PageUtil;
import com.bear.common.utils.PhoneUtil;
import com.bear.user.mapper.AppUserMapper;
import com.bear.user.mapper.UserCredentialsMapper;
import com.bear.user.mapper.UserRoleMapper;
import com.bear.user.service.AppUserService;
import com.bear.user.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author panda.
 * @since 2018-11-26 1:08.
 */
@Slf4j
@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserMapper appUserMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserCredentialsMapper userCredentialsMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addAppUser(AppUser appUser) {
        String username = appUser.getUsername();
        if (StringUtils.isBlank(username)) {
            throw new IllegalArgumentException("用户名不能为空");
        }

        // 防止用手机号直接当用户名，手机号要发短信验证
        if (PhoneUtil.checkPhone(username)) {
            throw new IllegalArgumentException("用户名要包含英文字符");
        }

        // 防止用邮箱直接当用户名，邮箱也要发送验证（暂未开发）
        String emailSplit = "@";
        if (username.contains(emailSplit)) {
            throw new IllegalArgumentException("用户名不能包含@");
        }
        String lineSplit = "|";
        if (username.contains(lineSplit)) {
            throw new IllegalArgumentException("用户名不能包含|字符");
        }

        if (StringUtils.isBlank(appUser.getPassword())) {
            throw new IllegalArgumentException("密码不能为空");
        }

        if (StringUtils.isBlank(appUser.getNickname())) {
            appUser.setNickname(username);
        }

        if (StringUtils.isBlank(appUser.getType())) {
            appUser.setType(UserType.APP.name());
        }

        UserCredential userCredential = userCredentialsMapper.findByUsername(appUser.getUsername());
        if (userCredential != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
        // 加密密码
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setEnabled(Boolean.TRUE);
        appUser.setCreateTime(new Date());
        appUser.setUpdateTime(appUser.getCreateTime());

        appUserMapper.save(appUser);
        userCredentialsMapper
                .save(new UserCredential(appUser.getUsername(), CredentialType.USERNAME.name(), appUser.getId()));
        log.info("添加用户：{}", appUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAppUser(AppUser appUser) {
        appUser.setUpdateTime(new Date());

        appUserMapper.update(appUser);
        log.info("修改用户：{}", appUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public LoginAppUser findByUsername(String username) {
        AppUser appUser = userCredentialsMapper.findUserByUsername(username);
        if (appUser != null) {
            LoginAppUser loginAppUser = new LoginAppUser();
            BeanUtils.copyProperties(appUser, loginAppUser);

            Set<Role> sysRoles = userRoleMapper.findRolesByUserId(appUser.getId());
            // 设置角色
            loginAppUser.setRoles(sysRoles);

            if (!CollectionUtils.isEmpty(sysRoles)) {
                Set<Long> roleIds = sysRoles.parallelStream().map(Role::getId).collect(Collectors.toSet());
                Set<Permission> sysPermissions = permissionService.findByRoleIds(roleIds);
                if (!CollectionUtils.isEmpty(sysPermissions)) {
                    Set<String> permissions = sysPermissions.parallelStream().map(Permission::getPermission)
                            .collect(Collectors.toSet());
// 设置权限集合
                    loginAppUser.setPermissions(permissions);
                }

            }

            return loginAppUser;
        }

        return null;
    }

    @Override
    public AppUser findById(Long id) {
        return appUserMapper.findById(id);
    }

    /**
     * 给用户设置角色<br>
     * 这里采用先删除老角色，再插入新角色
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setRoleToUser(Long id, Set<Long> roleIds) {
        AppUser appUser = appUserMapper.findById(id);
        if (appUser == null) {
            throw new IllegalArgumentException("用户不存在");
        }

        userRoleMapper.deleteUserRole(id, null);
        if (!CollectionUtils.isEmpty(roleIds)) {
            roleIds.forEach(roleId -> {
                userRoleMapper.saveUserRoles(id, roleId);
            });
        }

        log.info("修改用户：{}的角色，{}", appUser.getUsername(), roleIds);
    }

    /**
     * 修改密码
     *
     * @param id
     * @param oldPassword
     * @param newPassword
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePassword(Long id, String oldPassword, String newPassword) {
        AppUser appUser = appUserMapper.findById(id);
        if (StringUtils.isNoneBlank(oldPassword)) {
            // 旧密码校验
            if (!passwordEncoder.matches(oldPassword, appUser.getPassword())) {
                throw new IllegalArgumentException("旧密码错误");
            }
        }

        AppUser user = new AppUser();
        user.setId(id);
        // 加密密码
        user.setPassword(passwordEncoder.encode(newPassword));

        updateAppUser(user);
        log.info("修改密码：{}", user);
    }

    @Override
    public Page<AppUser> findUsers(Map<String, Object> params) {
        int total = appUserMapper.count(params);
        List<AppUser> list = Collections.emptyList();
        if (total > 0) {
            PageUtil.pageParamConver(params, true);

            list = appUserMapper.findData(params);
        }
        return new Page<>(total, list);
    }

    @Override
    public Set<Role> findRolesByUserId(Long userId) {
        return userRoleMapper.findRolesByUserId(userId);
    }

    /**
     * 绑定手机号
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void bindingPhone(Long userId, String phone) {
        UserCredential userCredential = userCredentialsMapper.findByUsername(phone);
        if (userCredential != null) {
            throw new IllegalArgumentException("手机号已被绑定");
        }

        AppUser appUser = appUserMapper.findById(userId);
        appUser.setPhone(phone);

        updateAppUser(appUser);
        log.info("绑定手机号成功,username:{}，phone:{}", appUser.getUsername(), phone);

        // 绑定成功后，将手机号存到用户凭证表，后续可通过手机号+密码或者手机号+短信验证码登陆
        userCredentialsMapper.save(new UserCredential(phone, CredentialType.PHONE.name(), userId));
    }

}