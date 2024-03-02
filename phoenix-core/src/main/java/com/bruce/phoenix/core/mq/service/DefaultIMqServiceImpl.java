package com.bruce.phoenix.core.mq.service;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/2 14:07
 * @Author Bruce
 */
@Slf4j
@Service
public class DefaultIMqServiceImpl<T> implements IMqService<T> {


    @Override
    public void proceed(T params) {
        log.info("[DefaultIMqServiceImpl#proceed] params: {}", JSONUtil.toJsonStr(params));
    }
}
