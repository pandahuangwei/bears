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
    void save(Menu menu);

    void update(Menu menu);

    void delete(Long id);

    void setMenuToRole(Long roleId, Set<Long> menuIds);

    List<Menu> findByRoles(Set<Long> roleIds);

    List<Menu> findAll();

    Menu findById(Long id);

    Set<Long> findMenuIdsByRoleId(Long roleId);
}
