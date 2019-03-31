package com.bear.user.mapper;

import com.bear.common.entity.user.Permission;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author panda.huangwei.
 * @since 2018-11-26 1:02.
 */
@Mapper
public interface PermissionMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_permission(permission, name, createTime, updateTime) values(#{permission}, #{name}, #{createTime}, #{createTime})")
    int save(Permission sysPermission);

    @Update("update sys_permission t set t.name = #{name}, t.permission = #{permission}, t.updateTime = #{updateTime} where t.id = #{id}")
    int update(Permission sysPermission);

    @Delete("delete from sys_permission where id = #{id}")
    int delete(Long id);

    @Select("select * from sys_permission t where t.id = #{id}")
    Permission findById(Long id);

    @Select("select * from sys_permission t where t.permission = #{permission}")
    Permission findByPermission(String permission);

    int count(Map<String, Object> params);

    List<Permission> findData(Map<String, Object> params);
}
