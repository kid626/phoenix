package com.bruce.phoenix.sys.controller;


import com.bruce.phoenix.sys.model.form.SysOpenapiForm;
import com.bruce.phoenix.sys.model.po.SysOpenapi;
import com.bruce.phoenix.sys.service.SysOpenapiService;
import com.frame.common.base.api.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2025 Bruce . All rights reserved.
 * @Desc Controller 接口
 * @ProjectName phoenix-sys
 * @Date 2025-06-27
 * @Author Bruce
 */
@RestController
@RequestMapping("/sys/openapi")
@Api(tags = "openapi相关接口")
public class SysOpenapiController {

    @Resource
    private SysOpenapiService service;

    @PostMapping("/generate")
    @ApiOperation("生成一个")
    public Result<SysOpenapi> save(@RequestBody @Validated SysOpenapiForm form) {
        SysOpenapi po = service.save(form);
        return Result.success(po);
    }

}
