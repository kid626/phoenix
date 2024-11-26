package com.bruce.phoenix.core.component.middleware;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 分布式序列生成器
     * 可以一次生成多个
     * sequence:20241122:key  ---  1
     *
     * @param key    键名称
     * @param length 长度
     * @param incr   增加值
     * @return 序列   长度为 incr
     */
    public List<String> generalSequence(String key, int length, int incr) {
        return redisComponent.lock4Run(LOCK + key, () -> {
            List<String> result = new ArrayList<>(incr);
            String finalKey = MessageFormat.format(PREFIX, DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_PATTERN), key);
            // 设置有效期  原子操作
            Long value = redisComponent.incrAndExpire(finalKey, incr, 24 * 3600);
            for (long i = value - incr + 1; i <= value; i++) {
                result.add(StrUtil.fillBefore(i + "", '0', length));
            }
            return result;
        }, 3);
    }

}
