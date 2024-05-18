package com.bruce.phoenix.core.config.threadpool;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/5/18 9:52
 * @Author Bruce
 */
@Configuration
@Slf4j
public class ThreadPoolMonitor {

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private ThreadPoolTaskScheduler scheduledThreadPoolExecutor;

    @PostConstruct
    public void monitor() {
        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            print(scheduledThreadPoolExecutor);
            print(threadPoolTaskExecutor);
        }, DateUtil.date(), 60000);
    }

    private void print(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        StringBuilder sb = new StringBuilder("[ThreadPoolConfig#print] threadPoolTaskExecutor=");
        sb.append(threadPoolTaskExecutor.getThreadPoolExecutor());
        log.info("{}", sb);
    }

    private void print(ThreadPoolTaskScheduler scheduledThreadPoolExecutor) {
        StringBuilder sb = new StringBuilder("[ThreadPoolConfig#print] scheduledThreadPoolExecutor=");
        sb.append(scheduledThreadPoolExecutor.getScheduledThreadPoolExecutor());
        log.info("{}", sb);
    }

}
