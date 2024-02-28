package com.bruce.demo.web.controller;

import com.bruce.demo.web.service.ThreadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/2/28 16:53
 * @Author Bruce
 */
@RestController
@RequestMapping("/hello")
public class ThreadController {

    @Resource
    private ThreadService helloService;

    /**
     * http://localhost:8080/hello/async/say
     */
    @GetMapping("/async/say")
    public String sayHelloAsync() {
        helloService.sayHelloAsync();
        return "hello";
    }

    /**
     * http://localhost:8080/hello/say
     */
    @GetMapping("/say")
    public String sayHello() {
        helloService.sayHello();
        return "hello";
    }

    /**
     * http://localhost:8080/hello/schedule/say
     */
    @GetMapping("/schedule/say")
    public String sayHelloSchedule() {
        helloService.sayHelloSchedule();
        return "hello";
    }

}
