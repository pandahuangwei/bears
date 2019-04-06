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
    /**
     * 保存角色
     *
     * @param role role
     * @return int
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_role(code, name, createTime, updateTime) values(#{code}, #{name}, #{createTime}, #{createTime})")
    int save(Role role);

    /**
     * 更新角色
     *
     * @param role role
     * @return int
     */
    @Update("update sys_role t set t.name = #{name} ,t.updateTime = #{updateTime} where t.id = #{id}")
    int update(Role role);

    /**
     * 根据id获取角色
     *
     * @param id id
     * @return Role
     */
    @Select("select * from sys_role t where t.id = #{id}")
    Role findById(Long id);

    /**
     * 查找角色
     *
     * @param code code
     * @return Role
     */
    @Select("select * from sys_role t where t.code = #{code}")
    Role findByCode(String code);

    /**
     * 删除角色
     *
     * @param id id
     * @return int
     */
    @Delete("delete from sys_role where id = #{id}")
    int delete(Long id);

    /**
     * 统计角色
     *
     * @param params params
     * @return int
     */
    int count(Map<String, Object> params);

    /**
     * 获取角色列表
     *
     * @param params params
     * @return Role
     */
    List<Role> findData(Map<String, Object> params);
}
