package com.bruce.phoenix.core.config.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ErrorHandler;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc 定时任务异常处理
 * @ProjectName thread-pool-demo
 * @Date 2023/3/22 16:15
 * @Author fzh
 */
@Slf4j
public class CustomErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable t) {
        log.warn("定时任务异常:ThreadName={},Err={}", Thread.currentThread().getName(), t.getMessage(), t);
    }
}
