package com.bear.user.mapper;

import com.bear.common.entity.user.Permission;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author panda.
 * @since 2018-11-26 1:02.
 */
@Mapper
public interface PermissionMapper {
    /**
     * 保存权限
     *
     * @param permission permission
     * @return int
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_permission(permission, name, createTime, updateTime) values(#{permission}, #{name}, #{createTime}, #{createTime})")
    int save(Permission permission);

    /**
     * 更新权限
     *
     * @param permission permission
     * @return int
     */
    @Update("update sys_permission t set t.name = #{name}, t.permission = #{permission}, t.updateTime = #{updateTime} where t.id = #{id}")
    int update(Permission permission);

    /**
     * 删除权限
     *
     * @param id id
     * @return int
     */
    @Delete("delete from sys_permission where id = #{id}")
    int delete(Long id);

    /**
     * 获取权限
     *
     * @param id id
     * @return permission
     */
    @Select("select * from sys_permission t where t.id = #{id}")
    Permission findById(Long id);

    /**
     * 获取权限
     *
     * @param permission permission
     * @return permission
     */
    @Select("select * from sys_permission t where t.permission = #{permission}")
    Permission findByPermission(String permission);

    /**
     * 计数权限
     *
     * @param params params
     * @return int
     */
    int count(Map<String, Object> params);

    /**
     * 获取权限
     *
     * @param params params
     * @return Permission
     */
    List<Permission> findData(Map<String, Object> params);
}
