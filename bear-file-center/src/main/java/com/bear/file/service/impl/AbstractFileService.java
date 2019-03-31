package com.bear.file.service.impl;

import com.bear.file.constants.FileSource;
import com.bear.file.entity.FileInfo;
import com.bear.file.mapper.FileMapper;
import com.bear.file.service.FileService;
import com.bear.file.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author panda.
 * @version 1.0.
 * @since 2018-11-26 15:10.
 */
@Slf4j
public abstract class AbstractFileService implements FileService {

    protected abstract FileMapper getFileMapper();

    @Override
    public FileInfo upload(MultipartFile file) throws Exception {
        FileInfo fileInfo = FileUtil.getFileInfo(file);
        // 先根据文件md5查询记录
        FileInfo oldFileInfo = getFileMapper().getById(fileInfo.getId());

        if (oldFileInfo != null) {// 如果已存在文件，避免重复上传同一个文件
            return oldFileInfo;
        }

        if (!fileInfo.getName().contains(".")) {
            throw new IllegalArgumentException("缺少后缀名");
        }

        uploadFile(file, fileInfo);

        fileInfo.setSource(fileSource().name());// 设置文件来源
        getFileMapper().save(fileInfo);// 将文件信息保存到数据库

        log.info("上传文件：{}", fileInfo);

        return fileInfo;
    }

    /**
     * 文件来源
     *
     * @return
     */
    protected abstract FileSource fileSource();

    /**
     * 上传文件
     *
     * @param file
     * @param fileInfo
     */
    protected abstract void uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception;

    @Override
    public void delete(FileInfo fileInfo) {
        deleteFile(fileInfo);
        getFileMapper().delete(fileInfo.getId());
        log.info("删除文件：{}", fileInfo);
    }

    /**
     * 删除文件资源
     *
     * @param fileInfo
     * @return
     */
    protected abstract boolean deleteFile(FileInfo fileInfo);
}
