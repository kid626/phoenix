package com.bruce.phoenix.oss.component;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.bruce.phoenix.core.model.oss.OssFileInfoModel;
import com.bruce.phoenix.oss.config.MinioProperties;
import com.bruce.phoenix.oss.model.query.FileInfoQuery;
import com.bruce.phoenix.oss.util.JWTUtil;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.policy.PolicyType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/29 15:54
 * @Author Bruce
 */
@Component
@Slf4j
public class MinioComponent {

    @Resource
    private MinioProperties properties;
    private MinioClient minioClient;

    @PostConstruct
    public void init() throws InvalidPortException, InvalidEndpointException {
        minioClient = new MinioClient(properties.getEndpoint(), properties.getAccessKey(), properties.getSecretKey());
    }

    /**
     * 文件上传
     */
    public OssFileInfoModel upload(MultipartFile file) throws Exception {
        OssFileInfoModel model = JWTUtil.getToken();
        String bucketName = model.getBucketName();
        bucketName = StrUtil.isBlank(bucketName) ? properties.getBucketName() : bucketName;

        // 检查存储桶是否已经存在
        boolean isExist = minioClient.bucketExists(bucketName);
        if (isExist) {
            log.info("桶已经存在!");
        } else {
            log.info("桶不存在，创建桶!");
            minioClient.makeBucket(bucketName);
            if (model.isPublic()) {
                minioClient.setBucketPolicy(bucketName, "**", PolicyType.READ_WRITE);
            }
        }

        //得到临时文件名
        String prefix = RandomUtil.randomString(16);
        String suffix = FileNameUtil.getSuffix(file.getOriginalFilename());
        String filename = prefix + "." + suffix;
        //得到文件前缀，按日期进行划分，多级构建 2020/03/02/
        String dateSt = DateUtil.formatDate(DateUtil.date());
        dateSt = dateSt.replace("-", "/");
        String path = dateSt + "/" + filename;

        minioClient.putObject(bucketName, path, file.getInputStream(), suffix);


        model.setFilePath(path);
        model.setBucketName(bucketName);

        return model;
    }

    public OssFileInfoModel upload(File tempFile, OssFileInfoModel model) throws Exception {
        String bucketName = model.getBucketName();
        bucketName = StrUtil.isBlank(bucketName) ? properties.getBucketName() : bucketName;

        // 检查存储桶是否已经存在
        boolean isExist = minioClient.bucketExists(bucketName);
        if (isExist) {
            log.info("桶已经存在!");
        } else {
            log.info("桶不存在，创建桶!");
            minioClient.makeBucket(bucketName);
            if (model.isPublic()) {
                minioClient.setBucketPolicy(bucketName, "**", PolicyType.READ_WRITE);
            }
        }

        //得到临时文件名
        String prefix = RandomUtil.randomString(16);
        String suffix = FileNameUtil.getSuffix(tempFile.getName());
        String filename = prefix + "." + suffix;
        //得到文件前缀，按日期进行划分，多级构建 2020/03/02/
        String dateSt = DateUtil.formatDate(DateUtil.date());
        dateSt = dateSt.replace("-", "/");
        String path = dateSt + "/" + filename;

        minioClient.putObject(bucketName, path, FileUtil.getInputStream(tempFile), suffix);

        model.setFilePath(path);
        model.setBucketName(bucketName);

        return model;
    }

    public String getUrl(FileInfoQuery query) {
        Integer expires = query.getExpires();
        if (null == expires) {
            expires = 7 * 24 * 3600;
        }
        try {
            // 公有桶无需加签名
            if (query.isPublic()) {
                return properties.getDomain() + "/" + query.getBucketName() + "/" + query.getFilePath();
            }
            String url = minioClient.presignedGetObject(query.getBucketName(), query.getFilePath(), expires, new HashMap<>());
            return url.replaceAll(properties.getEndpoint(), properties.getDomain());
        } catch (Exception e) {
            // 降级处理,不对外抛出异常
            log.warn("获取图片地址失败:{}", e.getMessage(), e);
            return "";
        }
    }

}
