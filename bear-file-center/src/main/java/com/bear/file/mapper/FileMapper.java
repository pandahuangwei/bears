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
    @Select("select * from file_info t where t.id = #{id}")
    FileInfo getById(String id);

    @Insert("insert into file_info(id, name, isImg, contentType, size, path, url, source, createTime) "
            + "values(#{id}, #{name}, #{isImg}, #{contentType}, #{size}, #{path}, #{url}, #{source}, #{createTime})")
    int save(FileInfo fileInfo);

    @Delete("delete from file_info where id = #{id}")
    int delete(String id);

    int count(Map<String, Object> params);

    List<FileInfo> findData(Map<String, Object> params);
}
