package com.bruce.phoenix.core.mq.listener;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.json.JSONUtil;
import com.bruce.phoenix.core.component.middleware.ScheduledThreadPoolComponent;
import com.bruce.phoenix.core.mq.component.MqComponent;
import com.bruce.phoenix.core.mq.model.MqModel;
import com.bruce.phoenix.core.mq.service.IMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc 队列监听
 * @ProjectName phoenix
 * @Date 2024/3/2 14:07
 * @Author Bruce
 */
@Component
@Slf4j
public class MqListener {

    @Resource
    private MqComponent mqComponent;
    @Resource
    private ScheduledThreadPoolComponent scheduledThreadPoolComponent;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private ThreadPoolTaskScheduler scheduledThreadPoolExecutor;

    private final Map<String, IMqService> map = new ConcurrentHashMap<>();

    @Autowired
    private MqListener(List<IMqService> list) {
        list.forEach(service -> {
            map.put(service.getType(), service);
        });
    }


    @PostConstruct
    public void init() {
        // 每半分钟处理一次
        scheduledThreadPoolExecutor.scheduleAtFixedRate(this::consume, DateUtil.date(), 30000);
    }

    private void consume() {
        log.info("[MqListener#consume] 开始");
        Set<String> topicSet = mqComponent.getAllTopic();
        for (String topic : topicSet) {
            MqModel<?> model = mqComponent.rightPopByTopic(topic);
            if (model != null) {
                if (Boolean.TRUE.equals(model.getScheduled())) {
                    // 周期性任务
                    scheduledThreadPoolComponent.startTask(model.getMessageId(),
                            () -> execute(model, topic), model.getStartDate(), model.getPeriod());
                } else {
                    // 非周期性的一次性任务
                    threadPoolTaskExecutor.execute(() -> execute(model, topic));
                }
            }
            log.warn("[MqListener#consume] topic: {} 队列为空", topic);
        }
        log.info("[MqListener#consume] 结束");
    }


    private <T> void execute(MqModel<T> model, String topic) {
        try {
            IMqService iMqService = map.get(model.getType());
            if (iMqService != null) {
                log.info("[MqListener#consume] topic: {} params: {}", topic,
                        JSONUtil.toJsonStr(model.getParams()));
                Object params = Convert.convert(ClassUtil.loadClass(model.getParamsType()),
                        model.getParams());
                iMqService.proceed(params);
            } else {
                log.warn("[MqListener#consume] topic: {} 无对应实现方法", topic);
            }
        } catch (Exception e) {
            log.error("[MqListener#consume] topic: {} 处理异常:{}", topic, e.getMessage(), e);
            if (model.getErrorCount() >= model.getErrorAllowCount()) {
                // 超过允许次数，不再重新入队了
                log.error("[MqListener#consume] topic: {} 重复次数超过{},不再处理", topic,
                        model.getErrorAllowCount());
                return;
            }
            long count = model.getErrorCount() + 1;
            log.error("[MqListener#consume] topic: {} 重试，次数{}", topic, count);
            model.setErrorCount(count);
            // 重新入队
            mqComponent.sendMessage(model);
        }
    }

}
