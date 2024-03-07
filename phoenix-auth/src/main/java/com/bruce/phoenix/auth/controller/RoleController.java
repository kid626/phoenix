package com.bruce.phoenix.auth.controller;


import com.bruce.phoenix.auth.model.form.RoleForm;
import com.bruce.phoenix.auth.model.po.Role;
import com.bruce.phoenix.auth.model.query.RoleQuery;
import com.bruce.phoenix.auth.model.vo.RoleVO;
import com.bruce.phoenix.auth.service.RoleService;
import com.bruce.phoenix.common.model.common.PageData;
import com.bruce.phoenix.common.model.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc Controller 接口
 * @ProjectName phoenix-auth
 * @Date 2024-03-05
 * @Author Bruce
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色相关接口")
public class RoleController {

    @Resource
    private RoleService service;

    @GetMapping("/v1/page")
    @ApiOperation("分页查询")
    public Result<PageData<RoleVO>> queryByPage(@Validated RoleQuery query) {
        PageData<RoleVO> pageData = service.queryByPage(query);
        return Result.success(pageData);
    }

    @GetMapping("/v1/detail/{id}")
    @ApiOperation("查询详情")
    public Result<Role> detail(@PathVariable(value = "id") Long id) {
        Role detail = service.queryById(id);
        return Result.success(detail);
    }

    @PostMapping("/v1/save")
    @ApiOperation("新增")
    public Result<String> save(@RequestBody @Validated RoleForm form) {
        Long id = service.save(form);
        return Result.success(String.valueOf(id));
    }

    @PostMapping("/v1/update")
    @ApiOperation("更新")
    public Result<String> update(@RequestBody @Validated RoleForm form) {
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
