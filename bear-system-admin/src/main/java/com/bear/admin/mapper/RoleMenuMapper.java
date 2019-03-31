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

    @Insert("insert into role_menu(roleId, menuId) values(#{roleId}, #{menuId})")
    int save(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    int delete(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    @Select("select t.menuId from role_menu t where t.roleId = #{roleId}")
    Set<Long> findMenuIdsByRoleId(Long roleId);

    List<Menu> findMenusByRoleIds(@Param("roleIds") Set<Long> roleIds);
}