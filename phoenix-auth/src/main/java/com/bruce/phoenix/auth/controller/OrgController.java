package com.bruce.phoenix.auth.controller;


import com.bruce.phoenix.auth.model.form.OrgForm;
import com.bruce.phoenix.auth.model.po.Org;
import com.bruce.phoenix.auth.model.query.OrgQuery;
import com.bruce.phoenix.auth.model.vo.OrgVO;
import com.bruce.phoenix.auth.service.OrgService;
import com.bruce.phoenix.common.model.common.PageData;
import com.bruce.phoenix.common.model.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 组织Controller 接口
 * @ProjectName phoenix-auth
 * @Date 2024-03-07
 * @Author Bruce
 */
@RestController
@RequestMapping("/org")
@Api(tags = "组织相关接口")
public class OrgController {

    @Resource
    private OrgService service;

    @GetMapping("/v1/page")
    @ApiOperation("分页查询组织")
    public Result<PageData<OrgVO>> queryByPage(@Validated OrgQuery query) {
        PageData<OrgVO> pageData = service.queryByPage(query);
        return Result.success(pageData);
    }

    @GetMapping("/v1/detail/{id}")
    @ApiOperation("查询组织详情")
    public Result<Org> detail(@PathVariable(value = "id") Long id) {
        Org detail = service.queryById(id);
        return Result.success(detail);
    }

    @PostMapping("/v1/save")
    @ApiOperation("新增组织")
    public Result<String> save(@RequestBody @Validated OrgForm form) {
        Long id = service.save(form);
        return Result.success(String.valueOf(id));
    }

    @PostMapping("/v1/update")
    @ApiOperation("更新组织")
    public Result<String> update(@RequestBody @Validated OrgForm form) {
        service.update(form);
        return Result.success();
    }


    @PostMapping("/v1/remove/{id}")
    @ApiOperation("删除组织")
    public Result<String> remove(@PathVariable(value = "id") Long id) {
        service.remove(id);
        return Result.success();
    }

}
