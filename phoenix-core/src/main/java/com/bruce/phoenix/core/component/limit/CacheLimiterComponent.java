package com.bruce.phoenix.core.component.limit;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc 基于内存的限流
 * @ProjectName phoenix
 * @Date 2024/2/28 16:19
 * @Author Bruce
 */
public class CacheLimiterComponent implements LimiterComponent {

    private final TimedCache<String, Node> cache;

    /**
     * 填充速率 个/秒
     */
    private final int rate;

    /**
     * 令牌桶容量
     */
    private final int capacity;

    /**
     * 当次请求消耗令牌个数
     */
    private final int requested;

    public CacheLimiterComponent(int rate, int capacity, int requested) {
        this.rate = rate;
        this.capacity = capacity;
        this.requested = requested;
        cache = CacheUtil.newTimedCache(0);
        cache.schedulePrune(60 * 1000);
        // 销毁时调用cache.cancelPruneSchedule();
    }

    @Override
    public synchronized boolean isLimited(String key) {
        String intern = key.intern();
        int ttl = BigDecimal.valueOf(capacity).divide(BigDecimal.valueOf(rate), 0, RoundingMode.CEILING).multiply(BigDecimal.valueOf(2)).intValue();
        long now = Instant.now().getEpochSecond();
        Long lastToken;
        Long timestamp;
        boolean allowed = false;
        synchronized (intern) {
            Node node = cache.get(key);
            if (node != null) {
                lastToken = node.getLastTokens();
                timestamp = node.getTimestamp();
            } else {
                node = new Node();
                lastToken = new Long(capacity);
                timestamp = 0L;
            }
            long timeDiff = now - timestamp;
            long filledTokens = Math.min(capacity, lastToken + (timeDiff * rate));
            if (filledTokens >= requested) {
                filledTokens = filledTokens - requested;
                allowed = true;

            }
            node.setLastTokens(filledTokens);
            node.setTimestamp(now);
            cache.put(intern, node, ttl * 1000);
        }
        return allowed;
    }


    @Data
    @NoArgsConstructor
    private static class Node {
        /**
         * 时间戳
         */
        private volatile Long timestamp;
        /**
         * 剩余令牌
         */
        private volatile Long lastTokens;
    }
}
