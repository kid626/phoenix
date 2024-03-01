package com.bruce.phoenix.core.event.component;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.bruce.phoenix.core.event.model.EventModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/1 16:10
 * @Author Bruce
 */
@Component
@Slf4j
public class EventComponent {

    @Resource
    private ApplicationContext applicationContext;

    public <T> void publishEvent(EventModel<T> model) {
        log.info("[EventComponent#publishEvent] params: {}", JSONUtil.toJsonStr(model));
        model.setEventId(RandomUtil.randomString(16));
        applicationContext.publishEvent(model);
    }

}
