package com.bruce.phoenix.common.config;

import cn.hutool.http.GlobalInterceptor;
import cn.hutool.http.HttpGlobalConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc hutool  HttpUtil 的超时配置
 * @ProjectName phoenix
 * @Date 2024/3/1 9:11
 * @Author Bruce
 */
@Configuration
@Slf4j
public class HttpUtilConfig {

    @Value("${http.timeout:3000}")
    private int timeout;

    @PostConstruct
    public void init() {
        GlobalInterceptor.INSTANCE.addRequestInterceptor(request -> log.info(String.valueOf(request)));
        GlobalInterceptor.INSTANCE.addResponseInterceptor(response -> log.info(String.valueOf(response)));
        HttpGlobalConfig.setTimeout(timeout);
    }


}
