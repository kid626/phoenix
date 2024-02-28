package com.bruce.phoenix.core.config.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName thread-pool-demo
 * @Date 2023/3/22 13:56
 * @Author fzh
 */
@Configuration
@EnableAsync
@Slf4j
@EnableScheduling
public class ThreadPoolConfig implements SchedulingConfigurer, AsyncConfigurer {

    /**
     * 队列最大长度1000
     */
    private static final int QUEUE_CAPACITY = 1000;

    /**
     * 线程池维护线程所允许的空闲时间 60s
     */
    private static final int KEEP_ALIVE_SECONDS = 60;

    /**
     * 前缀
     */
    private static final String PREFIX = "async-task-";
    /**
     * 定时任务前缀
     */
    private static final String SCHEDULE_PREFIX = "scheduled-thread-";

    @Bean
    @Primary
    public Executor threadPoolTaskExecutor() {
        // 通过Runtime方法来获取当前服务器cpu内核，根据cpu内核来创建核心线程数和最大线程数
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new CustomThreadPoolTaskExecutor();
        // 设置核心线程数
        threadPoolTaskExecutor.setCorePoolSize(availableProcessors);
        // 设置最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(availableProcessors * 2);
        // 设置工作队列大小
        threadPoolTaskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        // 设置允许的空闲时间
        threadPoolTaskExecutor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        // 自定义线程工厂
        threadPoolTaskExecutor.setThreadFactory(new ThreadFactory() {
            private final AtomicInteger threadCount = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                //创建一个线程
                Thread t = new Thread(r);
                t.setName(nextThreadName());
                //给创建的线程设置UncaughtExceptionHandler对象 里面实现异常的默认逻辑
                Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExceptionHandler());
                return t;
            }

            private String nextThreadName() {
                return PREFIX + this.threadCount.incrementAndGet();
            }
        });
        // 设置拒绝策略.当工作队列已满,线程数为最大线程数的时候,接收新任务抛出RejectedExecutionException异常
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // 初始化线程池
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    /**
     * 创建一个定长线程池，支持定时及周期性任务执行
     */
    @Bean
    public ThreadPoolTaskScheduler scheduledThreadPoolExecutor() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        // 大于等于 任务数量
        executor.setPoolSize(10);
        executor.setThreadNamePrefix(SCHEDULE_PREFIX);
        // 等待时长
        executor.setAwaitTerminationSeconds(60);
        executor.setErrorHandler(new CustomErrorHandler());
        //设置饱和策略
        //CallerRunsPolicy：线程池的饱和策略之一，当线程池使用饱和后，直接使用调用者所在的线程来执行任务；如果执行程序已关闭，则会丢弃该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }


    /**
     * 功能描述:  配置 @Scheduled 定时器所使用的线程池
     * 配置任务注册器：ScheduledTaskRegistrar 的任务调度器
     *
     * @param scheduledTaskRegistrar scheduledTaskRegistrar
     *                               "@Scheduled" 默认是单线程执行的，所以在需要的时候，我们可以设置一个线程池去执行定时任务。
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        //只可配置一种类型：taskScheduler
        scheduledTaskRegistrar.setTaskScheduler(scheduledThreadPoolExecutor());
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncUncaughtExceptionHandler();
    }


}
