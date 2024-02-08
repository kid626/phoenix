package com.bruce.phoenix.sys.controller;


import com.bruce.phoenix.common.model.common.PageData;
import com.bruce.phoenix.common.model.common.Result;
import com.bruce.phoenix.sys.model.query.SysLogQuery;
import com.bruce.phoenix.sys.model.vo.SysLogVO;
import com.bruce.phoenix.sys.service.SysLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2022 Bruce . All rights reserved.
 * @Desc 系统日志记录Controller 接口
 * @ProjectName demo
 * @Date 2022-6-20 9:50:39
 * @Author Bruce
 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    @Resource
    private SysLogService sysLogService;


    @GetMapping("/v1/page")
    @ApiOperation("分页查询")
    public Result<PageData<SysLogVO>> queryByPage(@Validated SysLogQuery query) {
        PageData<SysLogVO> pageData = sysLogService.queryByPage(query);
        return Result.success(pageData);
    }

}
