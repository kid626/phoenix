package com.bruce.phoenix.oss.controller;

import com.bruce.phoenix.core.model.oss.OssFileInfoModel;
import com.bruce.phoenix.core.model.oss.OssTokenModel;
import com.bruce.phoenix.oss.service.FileInfoService;
import com.bruce.phoenix.oss.util.JWTUtil;
import com.unicom.middleware.unicom.common.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/29 13:44
 * @Author Bruce
 */
@Api(tags = "文件上传模块")
@RestController
@RequestMapping("/oss")
@Slf4j
public class OssController {

    @Resource
    private FileInfoService fileInfoService;

    @PostMapping("/v1/token")
    @ApiOperation(value = "获取上传token")
    public Result<String> getToken(@Validated OssTokenModel form) {
        String token = JWTUtil.createToken(OssFileInfoModel.builder().bucketName(form.getBucketName()).build());
        return Result.success(token);
    }

    @PostMapping("/v1/upload")
    @ApiOperation(value = "文件上传")
    public Result<OssFileInfoModel> upload(@RequestParam("file") MultipartFile file) throws Exception {
        OssFileInfoModel model = fileInfoService.upload(file);
        return Result.success(model);
    }

}
