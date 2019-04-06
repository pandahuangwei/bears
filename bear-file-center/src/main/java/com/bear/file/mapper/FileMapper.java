package com.bear.file.mapper;

import com.bear.file.entity.FileInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author panda.
 * @version 1.0.
 * @since 2018-11-26 15:08.
 */
@Mapper
public interface FileMapper {
    /**
     * 获取文件信息
     *
     * @param id id
     * @return file
     */
    @Select("select * from file_info t where t.id = #{id}")
    FileInfo getById(String id);

    /**
     * 保存文件信息
     *
     * @param file file
     * @return int
     */
    @Insert("insert into file_info(id, name, isImg, contentType, size, path, url, source, createTime) "
            + "values(#{id}, #{name}, #{isImg}, #{contentType}, #{size}, #{path}, #{url}, #{source}, #{createTime})")
    int save(FileInfo file);

    /**
     * 删除文件信息
     *
     * @param id id
     * @return int
     */
    @Delete("delete from file_info where id = #{id}")
    int delete(String id);

    /**
     * 计数
     *
     * @param params param
     * @return int
     */
    int count(Map<String, Object> params);

    /**
     * 获取文件列表
     *
     * @param params param
     * @return list
     */
    List<FileInfo> findData(Map<String, Object> params);
}
