package com.bruce.phoenix.core.component.limit;

import com.bruce.phoenix.core.component.middleware.RedisComponent;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc redis 集合限流
 * @ProjectName phoenix
 * @Date 2024/2/28 16:06
 * @Author Bruce
 */
public class RedisZSetLimiterComponent implements LimiterComponent {

    private final RedisComponent redisComponent;

    /**
     * 限制次数
     */
    private final int count;

    /**
     * 时间窗口大小，单位毫秒
     */
    private final long timePeriod;


    public RedisZSetLimiterComponent(RedisComponent component) {
        this.redisComponent = component;
        this.count = 5;
        this.timePeriod = 1000;
    }

    public RedisZSetLimiterComponent(RedisComponent component, int count, long timePeriod) {
        this.redisComponent = component;
        this.count = count;
        this.timePeriod = timePeriod;
    }

    /**
     * 基于 zset 的滑动时间窗口限流算法
     * 在指定时间窗口，指定限制次数内，是否允许通过
     *
     * @param key 队列key
     * @return 是否允许通过
     */
    @Override
    public boolean isLimited(String key) {
        // 获取当前时间
        long nowTime = System.currentTimeMillis();
        // 移除一个时间段以前的
        redisComponent.removeRangeByScore(key, 0, (double) (nowTime - timePeriod));
        // 获取集合内元素总数
        Long size = redisComponent.count(key, (double) (nowTime - timePeriod), nowTime);
        // 如果队列没满
        if (size < count) {
            // 当前时间加入集合
            redisComponent.add(key, String.valueOf(nowTime), (double) nowTime);
            return false;
        }
        return true;
    }
}
