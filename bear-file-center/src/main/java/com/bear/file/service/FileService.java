package com.bear.file.service;

import com.bear.file.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author panda.
 * @version 1.0.
 * @since 2018-11-26 15:09.
 */
public interface FileService {
    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    FileInfo upload(MultipartFile file) throws Exception;

    /**
     * 删除文件
     *
     * @param fileInfo
     */
    void delete(FileInfo fileInfo);
}
