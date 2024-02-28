package com.bruce.phoenix.core.component.limit;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc 滑动窗口限流组件
 * @ProjectName phoenix
 * @Date 2024/2/28 16:05
 * @Author Bruce
 */
public class WindowLimiterComponent implements LimiterComponent {

    /**
     * 队列id和队列的映射关系，队列里面存储的是每一次通过时候的时间戳，这样可以使得程序里有多个限流队列
     */
    private final Map<String, List<Long>> MAP = new ConcurrentHashMap<>();

    /**
     * 限制次数
     */
    private final int count;

    /**
     * 时间窗口大小
     */
    private final long timePeriod;

    public WindowLimiterComponent(int count, long timePeriod) {
        this.count = count;
        this.timePeriod = timePeriod;
    }

    /**
     * 滑动时间窗口限流算法
     * 在指定时间窗口，指定限制次数内，是否允许通过
     *
     * @param id 队列id
     * @return 是否被限流
     */
    @Override
    public synchronized boolean isLimited(String id) {
        // 获取当前时间
        long nowTime = System.currentTimeMillis();
        // 根据队列id，取出对应的限流队列，若没有则创建
        List<Long> list = MAP.computeIfAbsent(id, k -> new LinkedList<>());
        // 如果队列还没满，则允许通过，并添加当前时间戳到队列开始位置
        if (list.size() < count) {
            list.add(0, nowTime);
            return false;
        }
        // 队列已满（达到限制次数），则获取队列中最早添加的时间戳
        Long lastTime = list.get(count - 1);
        // 用当前时间戳 减去 最早添加的时间戳
        if (nowTime - lastTime <= timePeriod) {
            // 若结果小于等于timePeriod，则说明在timePeriod内，通过的次数大于count
            // 不允许通过
            return true;
        } else {
            // 若结果大于timePeriod，则说明在timePeriod内，通过的次数小于等于count
            // 允许通过，并删除最早添加的时间戳，将当前时间添加到队列开始位置
            list.remove(count - 1);
            list.add(0, nowTime);
            return false;
        }
    }
}
