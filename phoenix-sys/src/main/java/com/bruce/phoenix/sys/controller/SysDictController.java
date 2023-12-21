package com.bruce.phoenix.sys.controller;


import com.bruce.phoenix.common.model.common.Err;
import com.bruce.phoenix.common.model.common.Result;
import com.bruce.phoenix.sys.model.po.SysDict;
import com.bruce.phoenix.sys.model.tree.dict.SysDictForm;
import com.bruce.phoenix.sys.model.tree.dict.SysDictTree;
import com.bruce.phoenix.sys.model.tree.dict.SysDictVO;
import com.bruce.phoenix.sys.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Copyright Copyright © 2022 Bruce . All rights reserved.
 * @Desc 系统数据字典Controller 接口
 * @ProjectName demo
 * @Date 2022-6-20 9:50:39
 * @Author Bruce
 */
@RestController
@RequestMapping("/sys/dict")
@Api(tags = "数据字典相关接口")
public class SysDictController {

    @Resource
    private SysDictService sysDictService;

    @PostMapping("/v1/save")
    @ApiOperation("新增")
    public Result<Long> save(@Validated @RequestBody SysDictForm form) {
        form.setId(null);
        return Result.success(sysDictService.save(form));
    }

    @PutMapping("/v1/update")
    @ApiOperation("更新")
    public Result<String> update(@Validated @RequestBody SysDictForm form) {
        if (form.getId() == null) {
            return Result.fail(Err.PARAM_ERROR.getCode(), "id不能为空!");
        }
        sysDictService.update(form);
        return Result.success();
    }

    @GetMapping("/v1/one/id/{id}")
    @ApiOperation("根据 id 查询")
    public Result<SysDict> queryById(@PathVariable Long id) {
        SysDict tree = sysDictService.queryById(id);
        return Result.success(tree);
    }

    @GetMapping("/v1/one/pid/{pid}")
    @ApiOperation("根据 父id 查询")
    public Result<List<SysDict>> queryByPid(@PathVariable Long pid) {
        List<SysDict> list = sysDictService.queryByPid(pid);
        return Result.success(list);
    }

    @GetMapping("/v1/one/code")
    @ApiOperation("根据编码查询")
    public Result<SysDict> queryByCode(@RequestParam String code) {
        SysDict sysDict = sysDictService.queryByCode(code);
        return Result.success(sysDict);
    }

    @GetMapping("/v1/tree")
    @ApiOperation("生成树")
    public Result<SysDictVO> tree() {
        SysDictVO vo = sysDictService.tree();
        return Result.success(vo);
    }

    @GetMapping("/v1/root")
    @ApiOperation("获取根节点")
    public Result<SysDictTree> getRoot() {
        SysDictTree tree = sysDictService.getRoot();
        return Result.success(tree);
    }

    @GetMapping("/v1/path/root/{id}")
    @ApiOperation("获取当前节点到根节点的路径")
    public Result<List<SysDictTree>> getRootPath(@PathVariable Long id) {
        List<SysDictTree> list = sysDictService.getRootPath(id);
        return Result.success(list);
    }

    @GetMapping("/v1/path/child/{id}")
    @ApiOperation("获取当前节点及其所有子节点")
    public Result<List<SysDictTree>> getChildNode(@PathVariable Long id) {
        List<SysDictTree> list = sysDictService.getChildNode(id);
        return Result.success(list);
    }

    @GetMapping("/v1/refresh")
    @ApiOperation("刷新缓存")
    public Result<String> refresh() {
        sysDictService.refresh();
        return Result.success();
    }

}
