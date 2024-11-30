package com.bruce.phoenix.common.util;

import cn.hutool.core.collection.CollUtil;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/11/30 16:15
 * @Author Bruce
 */
public class BatchProcessingUtil {

    /**
     * 串行批处理,多用于数据库分批插入，避免超过sql的最大限制
     *
     * @param list   需要处理的集合
     * @param size   每次处理页数
     * @param action 处理方法
     * @param <T>
     */
    public static <T> void batchProcessing(List<T> list, Integer size, Consumer<List<T>> action) {
        int page = 1;
        List<T> temp = list.stream().skip(page - 1).limit(size).collect(Collectors.toList());
        while (CollUtil.isNotEmpty(temp)) {
            action.accept(temp);
            page++;
            temp = temp.stream().skip((long) (page - 1) * size).limit(size).collect(Collectors.toList());
        }
    }

    /**
     * 并行批处理
     *
     * @param list   需要处理的集合
     * @param size   每次处理页数
     * @param action 处理方法
     * @param <T>
     */
    public static <T> void parallelBatchProcessing(List<T> list, Integer size, Consumer<List<T>> action) {
        int page = 1;
        List<T> temp = list.parallelStream().skip(page - 1).limit(size).collect(Collectors.toList());
        while (CollUtil.isNotEmpty(temp)) {
            action.accept(temp);
            page++;
            temp = temp.stream().skip((long) (page - 1) * size).limit(size).collect(Collectors.toList());
        }
    }

}
