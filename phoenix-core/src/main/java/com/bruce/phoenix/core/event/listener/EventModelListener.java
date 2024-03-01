package com.bruce.phoenix.core.event.listener;

import com.bruce.phoenix.core.event.model.EventModel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc 消息模型监听
 * @ProjectName phoenix
 * @Date 2024/3/1 16:07
 * @Author Bruce
 */
@Component
@Slf4j
public class EventModelListener {

    @Async
    @SneakyThrows
    @EventListener(EventModel.class)
    public <T> void onApplicationEvent(EventModel<T> eventModel) {
        log.info("[EventModelListener#onApplicationEvent] eventModel={}", eventModel);
        eventModel.getEventService().proceed(eventModel.getParams());
    }
}
