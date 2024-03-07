package com.bruce.phoenix.core.component.middleware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/7 16:49
 * @Author Bruce
 */
@Component
@Slf4j
public class ScheduledThreadPoolComponent {

    @Resource
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private final Map<String, ScheduledFuture<?>> FUTURE_MAP = new ConcurrentHashMap<>();

    /**
     * 开启任务
     *
     * @param key       唯一键，需要自行控制唯一
     * @param task      任务
     * @param startTime 开始时间
     * @param period    负数表示只执行一次   单位毫秒
     * @return ScheduledFuture<?>
     */
    public ScheduledFuture<?> startTask(String key, Runnable task, Date startTime, long period) {
        log.info("[ScheduledThreadPoolComponent#startTask] key={},startTime={},period={}", key, startTime, period);
        ScheduledFuture<?> scheduledFuture;
        if (period > 0) {
            scheduledFuture = threadPoolTaskScheduler.scheduleAtFixedRate(task, startTime, period);
        } else {
            // 一次性任务
            scheduledFuture = threadPoolTaskScheduler.schedule(task, startTime);
        }
        putFuture(key, scheduledFuture);
        return scheduledFuture;

    }

    /**
     * 停止任务
     *
     * @param key   唯一键
     */
    public void stopTask(String key) {
        log.info("[ScheduledThreadPoolComponent#stopTask] key={}", key);
        ScheduledFuture<?> scheduledFuture = getFuture(key);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
        removeFuture(key);
    }

    private void putFuture(String key, ScheduledFuture<?> future) {
        FUTURE_MAP.put(key, future);
    }

    private ScheduledFuture<?> getFuture(String key) {
        return FUTURE_MAP.get(key);
    }

    private void removeFuture(String key) {
        FUTURE_MAP.remove(key);
    }

}
