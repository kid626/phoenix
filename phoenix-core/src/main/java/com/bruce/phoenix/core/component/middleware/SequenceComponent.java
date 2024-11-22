package com.bruce.phoenix.core.component.middleware;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc 序列生成器
 * @ProjectName phoenix
 * @Date 2024/11/22 09:23
 * @Author Bruce
 */
@Component
public class SequenceComponent {

    @Resource
    private RedisComponent redisComponent;

    private static final String PREFIX = "sequence:{0}:{1}";
    private static final String LOCK = "lock:";


    /**
     * 分布式序列生成器
     * sequence:20241122:key  ---  1
     *
     * @param key    键名称
     * @param length 长度
     * @return 序列
     */
    public String generalSequence(String key, int length) {
        return redisComponent.lock4Run(LOCK + key, () -> {
            String finalKey = MessageFormat.format(PREFIX, DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_PATTERN), key);
            // 设置有效期  原子操作
            Long value = redisComponent.incrAndExpire(finalKey, 1, 24 * 3600);
            return StrUtil.fillBefore(value + "", '0', length);
        }, 3);
    }

}
