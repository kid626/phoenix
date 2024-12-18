package com.bruce.phoenix.common.util;

import com.bruce.phoenix.common.model.structure.CustomPriorityQueue;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/23 9:09
 * @Author Bruce
 */
public class StreamUtil {


    /**
     * 根据类的某个字段做去重，常配合 filter 使用
     *
     * @param keyExtractor 获取字段的方式
     * @param <T>          类
     * @return Predicate
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * 根据某个字段取 top max
     *
     * @param list 原始数据
     * @param g    某个字段
     * @param max  取 top max
     * @param <T>  表中数据结构
     * @return CustomPriorityQueue  队列，采用小顶堆的方式
     */
    public static <T> Map<String, Long> order(List<T> list, Function<? super T, ? extends String> g, Integer max) {
        Map<String, Long> map = list.stream().collect(Collectors.groupingBy(g, Collectors.counting()));
        LinkedHashMap<String, Long> orderedMap = new LinkedHashMap<>();
        CustomPriorityQueue<Map.Entry<String, Long>> result = map.entrySet().stream().collect(
                // 创建容器
                () -> new CustomPriorityQueue<Map.Entry<String, Long>>(Map.Entry.comparingByValue(), max),
                // 添加元素到容器
                (q, x) -> q.offer(x),
                // 合并容器
                (q1, q2) -> q1.addAll(q2));
        while (!result.isEmpty()) {
            Map.Entry<String, Long> entry = result.poll();
            orderedMap.put(entry.getKey(), entry.getValue());
        }
        return orderedMap;
    }

    /**
     * 两次分组，取最大值
     *
     * @param list  数据表
     * @param g1    第一次分组
     * @param g2    第二次分组
     * @param other 如果没有数据如何处理
     * @param <T>   表中数据结构
     * @return
     */
    public static <T> Map<String, Map<String, Long>> group(List<T> list,
                                                           Function<? super T, ? extends String> g1,
                                                           Function<? super T, ? extends String> g2,
                                                           Supplier<? extends Map.Entry<String, Long>> other) {
        Map<String, Map<String, Long>> result = new LinkedHashMap<>();
        Map<String, Map<String, Long>> collect = list.stream()
                .collect(Collectors.groupingBy(g1, Collectors.groupingBy(g2, Collectors.counting())));
        for (Map.Entry<String, Map<String, Long>> entry : collect.entrySet()) {
            String key = entry.getKey();
            Optional<Map.Entry<String, Long>> optional = entry.getValue().entrySet()
                    .stream().max(Map.Entry.comparingByValue());
            Map.Entry<String, Long> value = optional.orElseGet(other);
            HashMap<String, Long> valueMap = new HashMap<>();
            valueMap.put(value.getKey(), value.getValue());
            result.put(key, valueMap);
        }
        return result;
    }


}
