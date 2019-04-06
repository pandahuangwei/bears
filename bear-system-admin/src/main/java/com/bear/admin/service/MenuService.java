package com.bear.admin.service;

import com.bear.admin.entity.Menu;

import java.util.List;
import java.util.Set;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 15:52.
 */
public interface MenuService {
    /**
     * 保存菜单
     *
     * @param menu menu
     */
    void save(Menu menu);

    /**
     * 更新菜单
     *
     * @param menu menu
     */
    void update(Menu menu);

    /**
     * 删除菜单
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 设置菜单与角色的关系
     *
     * @param roleId  角色
     * @param menuIds 菜单
     */
    void setMenuToRole(Long roleId, Set<Long> menuIds);

    /**
     * 根据角色获取菜单
     *
     * @param roleIds 角色
     * @return menu
     */
    List<Menu> findByRoles(Set<Long> roleIds);

    /**
     * 获取所有菜单
     *
     * @return menu
     */
    List<Menu> findAll();

    /**
     * 根据id获取菜单
     *
     * @param id id
     * @return menu
     */
    Menu findById(Long id);

    /**
     * 根据角色获取菜单id列表
     *
     * @param roleId 角色
     * @return set
     */
    Set<Long> findMenuIdsByRoleId(Long roleId);
}
