package com.bruce.phoenix.sys.controller;


import com.bruce.phoenix.common.model.common.BasePageQuery;
import com.bruce.phoenix.common.model.common.PageData;
import com.bruce.phoenix.common.model.common.Result;
import com.bruce.phoenix.sys.model.form.SysConfigForm;
import com.bruce.phoenix.sys.model.vo.SysConfigVO;
import com.bruce.phoenix.sys.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Copyright Copyright © 2022 Bruce . All rights reserved.
 * @Desc 系统配置表Controller 接口
 * @ProjectName demo
 * @Date 2022-6-20 9:50:39
 * @Author Bruce
 */
@RestController
@RequestMapping("/sys/config")
@Api(tags = "系统配置相关接口")
public class SysConfigController {

    @Resource
    private SysConfigService sysConfigService;

    @GetMapping("/v1/all")
    @ApiOperation(value = "查询全部系统配置")
    public Result<List<SysConfigVO>> queryAll() {
        List<SysConfigVO> list = sysConfigService.queryAll();
        return Result.success(list);
    }

    @GetMapping("/v1/page")
    @ApiOperation(value = "分页查询全部系统配置")
    public Result<PageData<SysConfigVO>> queryAll(@Validated BasePageQuery query) {
        PageData<SysConfigVO> list = sysConfigService.queryAll(query);
        return Result.success(list);
    }

    @GetMapping("/v1/code")
    @ApiOperation(value = "根据code获取系统配置")
    public Result<SysConfigVO> queryByCode(String code) {
        SysConfigVO vo = sysConfigService.queryByCode(code);
        return Result.success(vo);
    }

    @PostMapping("/v1/save")
    @ApiOperation(value = "新增系统配置")
    public Result<String> save(@RequestBody @Validated SysConfigForm form) {
        long id = sysConfigService.save(form);
        return Result.success(String.valueOf(id));
    }

    @PostMapping("/v1/update")
    @ApiOperation(value = "更新系统配置")
    public Result<String> update(@RequestBody @Validated SysConfigForm form) {
        sysConfigService.update(form);
        return Result.success();
    }

    @PostMapping("/v1/remove/{id}")
    @ApiOperation(value = "删除系统配置")
    public Result<String> remove(@PathVariable Long id) {
        sysConfigService.remove(id);
        return Result.success();
    }


}
