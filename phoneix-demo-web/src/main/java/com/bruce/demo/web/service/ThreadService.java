package com.bruce.demo.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc 线程异常处理演示
 * @ProjectName phoenix
 * @Date 2024/2/28 16:53
 * @Author Bruce
 */
@Service
@Slf4j
public class ThreadService {


    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private ThreadPoolTaskScheduler scheduledThreadPoolExecutor;

    /**
     * '@Async'标注的方法，称之为异步方法
     * 这些方法将在执行的时候， 将会在独立的线程中被执行，
     * 调用者无需等待它的完成，即可继续其他的操作。
     */
    @Async
    public void sayHelloAsync() {
        fun();
    }

    public void sayHello() {
        threadPoolTaskExecutor.execute(() -> {
            fun();
        });

    }

    public void sayHelloSchedule() {
        scheduledThreadPoolExecutor.execute(() -> {
            fun();
        });
    }


    private void fun() {
        log.info("start say hello");
        log.info(Thread.currentThread().getName());
        log.info("hello");
        log.info("end say hello");
        int a = 1 / 0;
    }


}
