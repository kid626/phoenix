package com.bruce.demo.web.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import com.bruce.demo.web.model.constant.DemoConstant;
import com.bruce.demo.web.service.ThreadService;
import com.bruce.demo.web.ws.WebSocketHandler;
import com.bruce.phoenix.common.model.common.Result;
import com.bruce.phoenix.common.util.BaseHttpUtil;
import com.bruce.phoenix.core.annotation.Limiter;
import com.bruce.phoenix.core.event.component.EventComponent;
import com.bruce.phoenix.core.event.model.EventModel;
import com.bruce.phoenix.core.model.gateway.GatewaySignModel;
import com.bruce.phoenix.core.mq.component.MqComponent;
import com.bruce.phoenix.core.mq.model.MqModel;
import com.bruce.phoenix.core.util.GatewayHttpUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
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
@RequestMapping("/demo")
@Slf4j
public class DemoController {

    @Resource
    private ThreadService helloService;
    @Resource
    private EventComponent eventComponent;
    @Resource
    private MqComponent mqComponent;
    @Resource
    private WebSocketHandler webSocketHandler;

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
        GatewaySignModel model =
                GatewaySignModel.builder().appKey("appKey").appSecret("appSecret").host("http://ip" + ":port").path(
                        "/xxx").build();
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        String result = GatewayHttpUtil.get(model, null, params);
        return Result.success(result);
    }

    @GetMapping("/http/get")
    public Result<String> get() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("token", "123");
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "admin");
        String result = BaseHttpUtil.get("http://172.22.12.150:8001/http/get", headers, params);
        return Result.success(result);
    }

    @GetMapping("/limiter")
    @Limiter(key = "limiter:", message = "正在处理中，请勿重复操作!", expire = 60000, count = 1)
    public Result<String> limiter() {
        ThreadUtil.sleep(10000);
        return Result.success();
    }

    @GetMapping("/event")
    public Result<String> event() {
        EventModel<String> model = new EventModel<>();
        model.setParams("world");
        model.setEventService((params) -> {
            log.info("hello {}", params);
        });
        eventComponent.publishEvent(model);
        return Result.success();
    }

    @GetMapping("/mq")
    @ApiOperation("发送队列消息")
    public Result<String> mq() {
        MqModel<String> model = new MqModel<>();
        model.setParams("world");
        model.setTopic(DemoConstant.TOPIC_NAME_DEMO);
        model.setType(DemoConstant.MQ_TYPE_NAME_DEMO);
        mqComponent.sendMessage(model);
        return Result.success();
    }

    @GetMapping("/schedule/mq")
    @ApiOperation("发送队列消息-周期性")
    public Result<String> schedule() {
        MqModel<String> model = new MqModel<>();
        // 外部传入，作为周期任务的 key，方便后续管理
        model.setMessageId(RandomUtil.randomString(16));
        model.setParams("world");
        model.setTopic(DemoConstant.TOPIC_NAME_DEMO);
        model.setType(DemoConstant.MQ_TYPE_NAME_DEMO);
        model.setScheduled(Boolean.TRUE);
        // 一分钟后开始，每隔 5000 ms 执行一次
        model.setStartDate(DateUtil.date().offset(DateField.MINUTE, 1));
        model.setPeriod(5000L);
        mqComponent.sendMessage(model);
        return Result.success();
    }

    @GetMapping("/ws")
    @ApiOperation("发送 websocket 消息")
    public Result<String> send(String message) throws IOException {
        webSocketHandler.sendMessage("123", message);
        return Result.success();
    }

    @GetMapping("/broadcast")
    @ApiOperation("广播 websocket 消息")
    public Result<String> broadcastMessage(String message) throws IOException {
        webSocketHandler.broadcastMessage(message);
        return Result.success();
    }


}
