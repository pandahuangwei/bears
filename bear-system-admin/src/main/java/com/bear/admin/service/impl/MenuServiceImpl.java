package com.bear.admin.service.impl;

import com.bear.admin.entity.Menu;
import com.bear.admin.mapper.MenuMapper;
import com.bear.admin.mapper.RoleMenuMapper;
import com.bear.admin.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 16:05.
 */
@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Menu menu) {
        menu.setCreateTime(new Date());
        menu.setUpdateTime(menu.getCreateTime());

        menuMapper.save(menu);
        log.info("新增菜单：{}", menu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Menu menu) {
        menu.setUpdateTime(new Date());

        menuMapper.update(menu);
        log.info("修改菜单：{}", menu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        Menu menu = menuMapper.findById(id);

        menuMapper.deleteByParentId(id);
        menuMapper.delete(id);
        roleMenuMapper.delete(null, id);

        log.info("删除菜单：{}", menu);
    }

    /**
     * 给角色设置菜单<br>
     * 我们这里采用先删除后插入，这样更简单
     *
     * @param roleId
     * @param menuIds
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setMenuToRole(Long roleId, Set<Long> menuIds) {
        roleMenuMapper.delete(roleId, null);

        if (!CollectionUtils.isEmpty(menuIds)) {
            menuIds.forEach(menuId -> {
                roleMenuMapper.save(roleId, menuId);
            });
        }
    }

    @Override
    public List<Menu> findByRoles(Set<Long> roleIds) {
        return roleMenuMapper.findMenusByRoleIds(roleIds);
    }

    @Override
    public List<Menu> findAll() {
        return menuMapper.findAll();
    }

    @Override
    public Menu findById(Long id) {
        return menuMapper.findById(id);
    }

    @Override
    public Set<Long> findMenuIdsByRoleId(Long roleId) {
        return roleMenuMapper.findMenuIdsByRoleId(roleId);
    }

}