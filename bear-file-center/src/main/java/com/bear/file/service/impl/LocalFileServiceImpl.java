package com.bear.file.service.impl;

import com.bear.file.constants.FileSource;
import com.bear.file.entity.FileInfo;
import com.bear.file.mapper.FileMapper;
import com.bear.file.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

/**
 * @author panda.
 * @version 1.0.
 * @since 2018-11-26 15:14.
 */

@Service("localFileServiceImpl")
public class LocalFileServiceImpl extends AbstractFileService {

    @Autowired
    private FileMapper fileMapper;

    @Override
    protected FileMapper getFileMapper() {
        return fileMapper;
    }

    @Value("${file.local.urlPrefix}")
    private String urlPrefix;
    /**
     * 上传文件存储在本地的根路径
     */
    @Value("${file.local.path}")
    private String localFilePath;

    @Override
    protected FileSource fileSource() {
        return FileSource.LOCAL;
    }

    @Override
    protected void uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception {
        int index = fileInfo.getName().lastIndexOf(".");
        // 文件扩展名
        String fileSuffix = fileInfo.getName().substring(index);

        String suffix = "/" + LocalDate.now().toString().replace("-", "/") + "/" + fileInfo.getId() + fileSuffix;

        String path = localFilePath + suffix;
        String url = urlPrefix + suffix;
        fileInfo.setPath(path);
        fileInfo.setUrl(url);

        FileUtil.saveFile(file, path);
    }

    @Override
    protected boolean deleteFile(FileInfo fileInfo) {
        return FileUtil.deleteFile(fileInfo.getPath());
    }
}