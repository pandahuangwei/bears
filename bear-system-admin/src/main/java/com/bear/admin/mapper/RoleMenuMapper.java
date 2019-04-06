package com.bear.admin.mapper;

import com.bear.admin.entity.Menu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 15:50.
 */
@Mapper
public interface RoleMenuMapper {
    /**
     * 保存角色-功能信息
     *
     * @param roleId 角色
     * @param menuId 功能
     * @return int
     */
    @Insert("insert into role_menu(roleId, menuId) values(#{roleId}, #{menuId})")
    int save(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    /**
     * 删除角色-功能联系
     *
     * @param roleId 角色
     * @param menuId 功能菜单id
     * @return int
     */
    int delete(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    /**
     * 获取菜单
     *
     * @param roleId 菜单
     * @return 菜单id
     */
    @Select("select t.menuId from role_menu t where t.roleId = #{roleId}")
    Set<Long> findMenuIdsByRoleId(Long roleId);

    /**
     * 获取功能菜单列表
     *
     * @param roleIds 角色
     * @return list
     */
    List<Menu> findMenusByRoleIds(@Param("roleIds") Set<Long> roleIds);
}