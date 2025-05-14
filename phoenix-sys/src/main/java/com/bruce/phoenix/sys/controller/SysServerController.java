package com.bruce.phoenix.sys.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.bruce.phoenix.common.model.common.Result;
import com.bruce.phoenix.sys.util.SystemUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.util.Date;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/5/14 15:11
 * @Author Bruce
 */
@RestController
@RequestMapping("/sys/server")
@Api(tags = "系统服务相关接口")
@Slf4j
public class SysServerController {

    @GetMapping("/v1/monitor")
    @ApiOperation("查询服务监控")
    public Result<JSONObject> monitor() {
        JSONObject result = new JSONObject(8);
        try {
            SystemInfo si = new SystemInfo();
            OperatingSystem os = si.getOperatingSystem();
            HardwareAbstractionLayer hal = si.getHardware();
            // 系统信息
            result.put("sys", SystemUtil.getSystemInfo(os));
            // cpu 信息
            result.put("cpu", SystemUtil.getCpuInfo(hal.getProcessor()));
            // 内存信息
            result.put("memory", SystemUtil.getMemoryInfo(hal.getMemory()));
            // 交换区信息
            result.put("swap", SystemUtil.getSwapInfo(hal.getMemory()));
            // 磁盘
            result.put("disk", SystemUtil.getDiskInfo(os));
            result.put("time", DateUtil.format(new Date(), "HH:mm:ss"));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.success(result);
        }
        return Result.success(result);
    }


}
