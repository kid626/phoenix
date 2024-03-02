package com.bruce.phoenix.core.mq.component;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.bruce.phoenix.core.component.middleware.RedisComponent;
import com.bruce.phoenix.core.mq.model.MqModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc 队列组件
 * @ProjectName phoenix
 * @Date 2024/3/2 14:06
 * @Author Bruce
 */
@Component
@Slf4j
public class MqComponent {

    @Resource
    private RedisComponent redisComponent;

    /**
     * 队列 topic 名称集合，用来存放所有队列名称，方便遍历
     */
    private final static String QUEUE_TOPIC_SET_NAME = "QUEUE_TOPIC_SET";

    public synchronized <T> void sendMessage(MqModel<T> model) {
        log.info("[MqComponent#sendMessage] params: {}", JSONUtil.toJsonStr(model));
        model.setMessageId(RandomUtil.randomString(16));

        // 如果当前 topic 没有创建过了
        if (!redisComponent.isMember(QUEUE_TOPIC_SET_NAME, model.getTopic())) {
            // 新增 topic
            redisComponent.setAdd(QUEUE_TOPIC_SET_NAME, model.getTopic());
        }
        redisComponent.leftPush(model.getTopic(), JSONUtil.toJsonStr(model));

    }

    /**
     * 获取所有队列的 topic
     */
    public Set<String> getAllTopic() {
        return redisComponent.setMembers(QUEUE_TOPIC_SET_NAME);
    }

    public <T> MqModel<T> rightPopByTopic(String topic) {
        String value = redisComponent.rightPop(topic);
        if (StrUtil.isNotBlank(value)) {
            return JSONUtil.toBean(value, new TypeReference<MqModel<T>>() {
            }, false);
        }
        // 为空，则可以删除队列 topic
        redisComponent.setRemove(QUEUE_TOPIC_SET_NAME, topic);
        return null;
    }

}
