package com.bruce.phoenix.core.event.service;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc    默认实现
 * @ProjectName phoenix
 * @Date 2024/3/1 16:28
 * @Author Bruce
 */
@Slf4j
public class DefaultEventServiceImpl<T> implements EventService<T> {
    @Override
    public void proceed(T params) {
        log.info("[DefaultEventServiceImpl#proceed] params: {}", JSONUtil.toJsonStr(params));
    }
}
