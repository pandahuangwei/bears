package com.bear.file.service.impl;

import com.aliyun.oss.OSSClient;
import com.bear.file.constants.FileSource;
import com.bear.file.entity.FileInfo;
import com.bear.file.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author panda.
 * @version 1.0.
 * @since 2018-11-26 15:12.
 */
@Service("aliyunFileServiceImpl")
public class AliyunFileServiceImpl extends AbstractFileService {

    @Autowired
    private FileMapper fileMapper;

    @Override
    protected FileMapper getFileMapper() {
        return fileMapper;
    }

    @Override
    protected FileSource fileSource() {
        return FileSource.ALIYUN;
    }

    @Autowired
    private OSSClient ossClient;

    @Value("${file.aliyun.bucketName}")
    private String bucketName;
    @Value("${file.aliyun.domain}")
    private String domain;

    @Override
    protected void uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception {
        ossClient.putObject(bucketName, fileInfo.getName(), file.getInputStream());
        fileInfo.setUrl(domain + "/" + fileInfo.getName());
    }

    @Override
    protected boolean deleteFile(FileInfo fileInfo) {
        ossClient.deleteObject(bucketName, fileInfo.getName());
        return true;
    }

}
