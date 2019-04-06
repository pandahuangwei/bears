package com.bear.user.mapper;

import com.bear.common.entity.user.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author panda.
 * @since 2018-11-26 1:03.
 */
@Mapper
public interface RoleMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_role(code, name, createTime, updateTime) values(#{code}, #{name}, #{createTime}, #{createTime})")
    int save(Role sysRole);

    @Update("update sys_role t set t.name = #{name} ,t.updateTime = #{updateTime} where t.id = #{id}")
    int update(Role sysRole);

    @Select("select * from sys_role t where t.id = #{id}")
    Role findById(Long id);

    @Select("select * from sys_role t where t.code = #{code}")
    Role findByCode(String code);

    @Delete("delete from sys_role where id = #{id}")
    int delete(Long id);

    int count(Map<String, Object> params);

    List<Role> findData(Map<String, Object> params);
}
