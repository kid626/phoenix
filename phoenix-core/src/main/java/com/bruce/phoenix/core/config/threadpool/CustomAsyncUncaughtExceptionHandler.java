package com.bruce.phoenix.core.config.threadpool;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc `@Async` 注解异常处理，区分 submit 和 execute 的区别
 * @ProjectName thread-pool-demo
 * @Date 2023/3/22 16:13
 * @Author fzh
 */
@Slf4j
public class CustomAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.info("AsyncError: Method: {}, Param: {}, Error: {}",
                method.getName(), JSONObject.toJSONString(params), ex.getMessage(), ex);
    }
}
