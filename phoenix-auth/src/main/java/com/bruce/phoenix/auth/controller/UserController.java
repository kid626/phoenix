package com.bruce.phoenix.auth.controller;


import com.bruce.phoenix.auth.model.po.User;
import com.bruce.phoenix.auth.model.form.UserForm;
import com.bruce.phoenix.auth.model.vo.UserVO;
import com.bruce.phoenix.auth.model.query.UserQuery;
import com.bruce.phoenix.auth.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bruce.phoenix.common.model.common.PageData;
import com.bruce.phoenix.common.model.common.Result;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc Controller 接口
 * @ProjectName phoenix-auth
 * @Date 2024-03-05
 * @Author Bruce
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService service;

    @GetMapping("/v1/page")
    @ApiOperation("分页查询")
    public Result<PageData<UserVO>> queryByPage(@Validated UserQuery query) {
        PageData<UserVO> pageData = service.queryByPage(query);
        return Result.success(pageData);
    }

    @GetMapping("/v1/detail/{id}")
    @ApiOperation("查询详情")
    public Result<User> detail(@PathVariable(value = "id") Long id) {
        User detail = service.queryById(id);
        return Result.success(detail);
    }

    @PostMapping("/v1/save")
    @ApiOperation("新增")
    public Result<String> save(@RequestBody @Validated UserForm form) {
        Long id = service.save(form);
        return Result.success(String.valueOf(id));
    }

    @PostMapping("/v1/update")
    @ApiOperation("更新")
    public Result<String> update(@RequestBody @Validated UserForm form) {
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
