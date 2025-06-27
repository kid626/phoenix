package com.bruce.phoenix.sys.controller;


import com.bruce.phoenix.common.model.common.PageData;
import com.bruce.phoenix.sys.model.form.SysOpenapiForm;
import com.bruce.phoenix.sys.model.po.SysOpenapi;
import com.bruce.phoenix.sys.model.query.SysOpenapiQuery;
import com.bruce.phoenix.sys.model.vo.SysOpenapiVO;
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
@RequestMapping("/sysOpenapi")
@Api(tags = "相关接口")
public class SysOpenapiController {

    @Resource
    private SysOpenapiService service;

    @GetMapping("/v1/page")
    @ApiOperation("分页查询")
    public Result<PageData<SysOpenapiVO>> queryByPage(@Validated SysOpenapiQuery query) {
        PageData<SysOpenapiVO> pageData = service.queryByPage(query);
        return Result.success(pageData);
    }

    @GetMapping("/v1/detail/{id}")
    @ApiOperation("查询详情")
    public Result<SysOpenapi> detail(@PathVariable(value = "id") Long id) {
        SysOpenapi detail = service.queryById(id);
        return Result.success(detail);
    }

    @PostMapping("/v1/save")
    @ApiOperation("新增")
    public Result<String> save(@RequestBody @Validated SysOpenapiForm form) {
        Long id = service.save(form);
        return Result.success(String.valueOf(id));
    }

    @PostMapping("/v1/update")
    @ApiOperation("更新")
    public Result<String> update(@RequestBody @Validated SysOpenapiForm form) {
        service.update(form);
        return Result.success();
    }


    @PostMapping("/v1/remove/{id}")
    @ApiOperation("删除")
    public Result<String> remove(@PathVariable(value = "id") Long id) {
        service.remove(id);
        return Result.success();
    }

}
