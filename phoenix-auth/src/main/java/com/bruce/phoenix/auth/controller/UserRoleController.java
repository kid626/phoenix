package com.bruce.phoenix.auth.controller;


import com.bruce.phoenix.auth.model.form.UserRoleForm;
import com.bruce.phoenix.auth.model.po.UserRole;
import com.bruce.phoenix.auth.model.query.UserRoleQuery;
import com.bruce.phoenix.auth.model.vo.UserRoleVO;
import com.bruce.phoenix.auth.service.UserRoleService;
import com.bruce.phoenix.common.model.common.PageData;
import com.bruce.phoenix.common.model.common.Result;
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
@RequestMapping("/user/role")
public class UserRoleController {

    @Resource
    private UserRoleService service;

    @GetMapping("/v1/page")
    @ApiOperation("分页查询")
    public Result<PageData<UserRoleVO>> queryByPage(@Validated UserRoleQuery query) {
        PageData<UserRoleVO> pageData = service.queryByPage(query);
        return Result.success(pageData);
    }

    @GetMapping("/v1/detail/{id}")
    @ApiOperation("查询详情")
    public Result<UserRole> detail(@PathVariable(value = "id") Long id) {
        UserRole detail = service.queryById(id);
        return Result.success(detail);
    }

    @PostMapping("/v1/save")
    @ApiOperation("新增")
    public Result<String> save(@RequestBody @Validated UserRoleForm form) {
        Long id = service.save(form);
        return Result.success(String.valueOf(id));
    }

    @PostMapping("/v1/update")
    @ApiOperation("更新")
    public Result<String> update(@RequestBody @Validated UserRoleForm form) {
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
