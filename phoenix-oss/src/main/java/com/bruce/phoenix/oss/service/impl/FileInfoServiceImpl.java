package com.bruce.phoenix.oss.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.bruce.phoenix.common.model.enums.YesOrNoEnum;
import com.bruce.phoenix.core.model.oss.OssFileInfoModel;
import com.bruce.phoenix.core.model.oss.OssQueryModel;
import com.bruce.phoenix.oss.component.MinioComponent;
import com.bruce.phoenix.oss.dao.FileInfoDao;
import com.bruce.phoenix.oss.model.converter.FileInfoConverter;
import com.bruce.phoenix.oss.model.enums.FileOriginEnum;
import com.bruce.phoenix.oss.model.enums.FileTypeEnum;
import com.bruce.phoenix.oss.model.form.FileInfoForm;
import com.bruce.phoenix.oss.model.po.FileInfo;
import com.bruce.phoenix.oss.model.query.FileInfoQuery;
import com.bruce.phoenix.oss.service.FileInfoService;
import com.bruce.phoenix.sys.model.po.SysDict;
import com.bruce.phoenix.sys.service.SysDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc service 实现类
 * @ProjectName phoenix-oss
 * @Date 2024-09-29
 * @Author Bruce
 */
@Service
@Slf4j
public class FileInfoServiceImpl implements FileInfoService {

    @Resource
    private FileInfoDao dao;
    @Resource
    private SysDictService sysDictService;
    @Resource
    private MinioComponent minioComponent;
    @Value("${kkfile.url:''}")
    private String viewUrl;

    private static final FileInfoConverter CONVERTER = new FileInfoConverter();

    @Override
    public Long save(FileInfoForm form) {
        FileInfo po = new FileInfo();
        CONVERTER.convert2Po(form, po);
        return dao.save(po);
    }

    @Override
    public void update(FileInfoForm form) {
        FileInfo po = new FileInfo();
        CONVERTER.convert2Po(form, po);
        dao.update(po);
    }

    @Override
    public void remove(Long id) {
        dao.remove(id);
    }


    @Override
    public FileInfo queryById(Long id) {
        return dao.queryById(id);
    }


    @Override
    public OssFileInfoModel upload(MultipartFile file) throws Exception {
        // 解析文件信息
        byte[] bytes = file.getBytes();
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileSize(file.getSize());
        fileInfo.setIdentifier(DigestUtil.md5Hex(bytes));
        fileInfo.setOriginFileName(file.getOriginalFilename());
        fileInfo.setExtendName(FileNameUtil.getSuffix(file.getOriginalFilename()));
        fileInfo.setFileTypeCode(getFileTypeByExt(fileInfo.getExtendName()));
        fileInfo.setFileTypeName(FileTypeEnum.getByCode(fileInfo.getFileTypeCode()));

        // 上传到 minio
        OssFileInfoModel model = minioComponent.upload(file);

        // 上传完 回填 桶名和文件 path
        fileInfo.setBucketName(model.getBucketName());
        fileInfo.setPath(model.getFilePath());
        fileInfo.setIsPublic(model.isPublic() ? YesOrNoEnum.YES.getCode() : YesOrNoEnum.NO.getCode());

        fileInfo.setOrigin(FileOriginEnum.CLIENT.getCode());

        // 保存入库
        dao.save(fileInfo);
        // 返回文件 id
        model.setFileId(fileInfo.getId());
        model.setFileName(fileInfo.getOriginFileName());

        // 获取文件临时 url
        FileInfoQuery query = FileInfoQuery.builder().isPublic(model.isPublic()).filePath(model.getFilePath()).bucketName(model.getBucketName()).build();
        String url = minioComponent.getUrl(query);
        model.setFileUrl(url);
        return model;
    }

    @Override
    public String view(OssQueryModel model) {
        String url = url(model);
        return MessageFormat.format(viewUrl, URLEncodeUtil.encodeAll(url));
    }

    @Override
    public String url(OssQueryModel model) {
        // 根据条件回表查
        FileInfo fileInfo = queryByIdOrPath(model);
        FileInfoQuery query = FileInfoQuery.builder().bucketName(fileInfo.getBucketName()).filePath(fileInfo.getPath()).isPublic(YesOrNoEnum.YES.getCode().equals(fileInfo.getIsPublic())).build();
        return minioComponent.getUrl(query);
    }

    @Override
    public OssFileInfoModel upload(OssFileInfoModel model) throws Exception {
        // 生成临时文件
        File tempFile = new File("/temp/" + model.getFileName());
        try {
            String fileBase64 = model.getFileBase64();
            tempFile = Base64.decodeToFile(fileBase64, tempFile);

            // 解析文件信息
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileSize(tempFile.length());
            fileInfo.setIdentifier(DigestUtil.md5Hex(fileBase64));
            fileInfo.setOriginFileName(model.getFileName());
            fileInfo.setExtendName(FileNameUtil.getSuffix(model.getFileName()));
            fileInfo.setFileTypeCode(getFileTypeByExt(fileInfo.getExtendName()));
            fileInfo.setFileTypeName(FileTypeEnum.getByCode(fileInfo.getFileTypeCode()));

            // 上传到 minio
            model = minioComponent.upload(tempFile, model);

            // 上传完 回填 桶名和文件 path
            fileInfo.setBucketName(model.getBucketName());
            fileInfo.setPath(model.getFilePath());
            fileInfo.setIsPublic(model.isPublic() ? YesOrNoEnum.YES.getCode() : YesOrNoEnum.NO.getCode());

            fileInfo.setOrigin(FileOriginEnum.GATEWAY.getCode());

            // 保存入库
            dao.save(fileInfo);
            // 返回文件 id
            model.setFileId(fileInfo.getId());
            model.setFileName(fileInfo.getOriginFileName());

            // 获取文件临时 url
            FileInfoQuery query = FileInfoQuery.builder().isPublic(model.isPublic()).filePath(model.getFilePath()).bucketName(model.getBucketName()).build();
            String url = minioComponent.getUrl(query);
            model.setFileUrl(url);
            return model;
        } finally {
            // 临时文件删除
            tempFile.delete();
        }
    }

    /**
     * 根据后缀名获取文件类型
     */
    private String getFileTypeByExt(String ext) {
        SysDict sysDict = sysDictService.queryByCode(ext);
        if (sysDict != null) {
            String pCode = sysDict.getPCode();
            if (FileTypeEnum.LOOKUP.containsKey(pCode) && !FileTypeEnum.ALL.getCode().equals(pCode)) {
                return pCode;
            }
        }
        return FileTypeEnum.OTHER.getCode();
    }

    private FileInfo queryByIdOrPath(OssQueryModel model) {
        // 优先根据 id 查询
        if (model.getFileId() != null) {
            return queryById(model.getFileId());
        }
        // 根据 path 查询
        return dao.queryByPath(model.getFilePath());
    }

    private void base64ToFile(String absPath, String base64) throws IOException {
        byte[] decodedBytes = Base64.decode(base64);
        Files.write(Paths.get(absPath), decodedBytes);
    }
}
