package com.bruce.demo.web.controller;

import com.bruce.demo.web.service.ThreadService;
import com.bruce.phoenix.common.model.common.Result;
import com.bruce.phoenix.core.model.gateway.GatewaySignModel;
import com.bruce.phoenix.core.util.GatewayHttpUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/2/28 16:53
 * @Author Bruce
 */
@RestController
@RequestMapping("/hello")
public class DemoController {

    @Resource
    private ThreadService helloService;

    @GetMapping("/async/say")
    public Result<String> sayHelloAsync() {
        helloService.sayHelloAsync();
        return Result.success();
    }

    @GetMapping("/say")
    public Result<String> sayHello() {
        helloService.sayHello();
        return Result.success();
    }

    @GetMapping("/schedule/say")
    public Result<String> sayHelloSchedule() {
        helloService.sayHelloSchedule();
        return Result.success();
    }


    @GetMapping("/gateway")
    public Result<String> gateway(String token) {
        GatewaySignModel model = GatewaySignModel.builder()
                .appKey("appKey")
                .appSecret("appSecret")
                .host("http://ip:port")
                .path("/xxx").build();
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        String result = GatewayHttpUtil.get(model, null, params);
        return Result.success(result);
    }


}