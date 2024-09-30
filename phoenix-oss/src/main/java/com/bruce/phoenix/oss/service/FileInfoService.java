package com.bruce.phoenix.oss.service;

import com.bruce.phoenix.core.model.oss.OssFileInfoModel;
import com.bruce.phoenix.core.model.oss.OssQueryModel;
import com.bruce.phoenix.oss.model.form.FileInfoForm;
import com.bruce.phoenix.oss.model.po.FileInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc service 层
 * @ProjectName phoenix-oss
 * @Date 2024-09-29
 * @Author Bruce
 */
public interface FileInfoService {

    /**
     * 新增
     *
     * @param form FileInfoForm
     * @return 主键
     */
    Long save(FileInfoForm form);


    /**
     * 更新
     *
     * @param form FileInfoForm
     */
    void update(FileInfoForm form);

    /**
     * 删除
     *
     * @param id 主键
     */
    void remove(Long id);

    /**
     * 按主键查询
     */
    FileInfo queryById(Long id);

    /**
     * 文件上传
     */
    OssFileInfoModel upload(MultipartFile file) throws Exception;

    /**
     * 文件预览地址
     */
    String view(OssQueryModel model);

    /**
     * 文件完整路径
     */
    String url(OssQueryModel model);

    /**
     * 文件上传,适用于网关上传
     */
    OssFileInfoModel upload(OssFileInfoModel model) throws Exception ;
}
