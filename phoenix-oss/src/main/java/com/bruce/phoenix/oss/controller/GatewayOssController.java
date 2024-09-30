package com.bruce.phoenix.oss.controller;

import com.bruce.phoenix.core.model.oss.OssFileInfoModel;
import com.bruce.phoenix.core.model.oss.OssQueryModel;
import com.bruce.phoenix.core.model.oss.OssTokenModel;
import com.bruce.phoenix.oss.service.FileInfoService;
import com.bruce.phoenix.oss.util.JWTUtil;
import com.unicom.middleware.unicom.common.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/29 13:44
 * @Author Bruce
 */
@Api(tags = "网关-文件上传模块")
@RestController
@RequestMapping("/gateway/oss")
@Slf4j
public class GatewayOssController {

    @Resource
    private FileInfoService fileInfoService;

    @PostMapping("/v1/token")
    @ApiOperation(value = "获取上传token")
    public Result<String> getToken(@Validated OssTokenModel model) {
        String token = JWTUtil.createToken(OssFileInfoModel.builder().bucketName(model.getBucketName()).build());
        return Result.success(token);
    }

    @PostMapping("/v1/upload")
    @ApiOperation(value = "文件上传-只支持base64")
    public Result<OssFileInfoModel> upload(@Validated @RequestBody OssFileInfoModel model) throws Exception {
        model = fileInfoService.upload(model);
        // 减少网关层的交互
        model.setFileBase64("");
        return Result.success(model);
    }

    @PostMapping("/v1/view")
    @ApiOperation("预览地址")
    public Result<String> view(@RequestBody @Validated OssQueryModel model) {
        String url = fileInfoService.view(model);
        return Result.success(url);
    }

    @PostMapping("/v1/url")
    @ApiOperation("获取完整访问地址")
    public Result<String> url(@RequestBody @Validated OssQueryModel query) {
        String url = fileInfoService.url(query);
        return Result.success(url);
    }

}
