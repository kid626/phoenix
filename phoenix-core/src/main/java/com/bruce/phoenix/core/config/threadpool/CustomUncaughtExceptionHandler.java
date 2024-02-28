package com.bruce.phoenix.core.config.threadpool;

import lombok.extern.slf4j.Slf4j;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc 线程异常处理
 * @ProjectName thread-pool-demo
 * @Date 2023/3/22 16:14
 * @Author fzh
 */
@Slf4j
public class CustomUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.info("线程:{}发生异常:{}", t.getName(), e.getMessage(), e);
    }
}
