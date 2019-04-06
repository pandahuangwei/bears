package com.bear.admin.mapper;

import com.bear.admin.entity.Menu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author panda.
 * @version 1.0.
 * @since 2019-02-26 15:46.
 */
@Mapper
public interface MenuMapper {
    /**
     * 保存菜单
     *
     * @param menu menu
     * @return int
     */
    @Insert("insert into menu(parentId, name, url, css, sort, createTime, updateTime) "
            + "values (#{parentId}, #{name}, #{url}, #{css}, #{sort}, #{createTime}, #{updateTime})")
    int save(Menu menu);

    /**
     * 更新菜单
     *
     * @param menu menu
     * @return int
     */
    int update(Menu menu);

    /**
     * 根据id获取菜单详情
     *
     * @param id id
     * @return 菜单
     */
    @Select("select * from menu t where t.id = #{id}")
    Menu findById(Long id);

    /**
     * 删除菜单
     *
     * @param id id
     * @return int
     */
    @Delete("delete from menu where id = #{id}")
    int delete(Long id);

    /**
     * 删除子菜单
     *
     * @param id 父菜单id
     * @return int
     */
    @Delete("delete from menu where parentId = #{id}")
    int deleteByParentId(Long id);

    /**
     * 获取所有菜单
     *
     * @return list
     */
    @Select("select * from menu t order by t.sort")
    List<Menu> findAll();
}