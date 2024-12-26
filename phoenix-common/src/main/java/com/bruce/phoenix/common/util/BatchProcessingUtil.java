package com.bruce.phoenix.common.util;

import cn.hutool.core.collection.CollUtil;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
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
            temp = list.stream().skip((long) (page - 1) * size).limit(size).collect(Collectors.toList());
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
            List<T> finalTemp = temp;
            CompletableFuture.runAsync(() -> action.accept(finalTemp));
            page++;
            temp = list.stream().skip((long) (page - 1) * size).limit(size).collect(Collectors.toList());
        }
    }

    /**
     * 串行处理 有返回值
     *
     * @param list   需要处理的集合
     * @param size   每次处理页数
     * @param action 处理方法
     * @param merge  结果合并方法
     * @param r0     结果初始值
     * @param <T>    集合元素类型
     * @param <R>    返回值类型
     * @return 串行处理 有返回值
     */
    public static <T, R> R batchProcessing(List<T> list, Integer size, Function<List<T>, R> action, BiFunction<? super R, ? super R, ? extends R> merge, R r0) {
        int page = 1;
        List<T> temp = list.stream().skip(page - 1).limit(size).collect(Collectors.toList());
        while (CollUtil.isNotEmpty(temp)) {
            R r = action.apply(temp);
            r0 = merge.apply(r0, r);
            page++;
            temp = list.stream().skip((long) (page - 1) * size).limit(size).collect(Collectors.toList());
        }
        return r0;
    }

    /**
     * 并行处理 有返回值
     *
     * @param list   需要处理的集合
     * @param size   每次处理页数
     * @param action 处理方法
     * @param merge  结果合并方法
     * @param <T>    集合元素类型
     * @param <R>    返回值类型
     * @return 串行处理 有返回值
     */
    public static <T, R> R parallelBatchProcessing(List<T> list, Integer size, Function<List<T>, R> action, BiFunction<? super R, ? super R, ? extends R> merge) throws ExecutionException, InterruptedException {
        List<CompletableFuture<R>> futures = CollUtil.newArrayList();
        int page = 1;
        List<T> temp = list.stream().skip(page - 1).limit(size).collect(Collectors.toList());
        while (CollUtil.isNotEmpty(temp)) {
            List<T> finalTemp = temp;
            CompletableFuture<R> future = CompletableFuture.supplyAsync(() -> action.apply(finalTemp));
            futures.add(future);
            page++;
            temp = list.stream().skip((long) (page - 1) * size).limit(size).collect(Collectors.toList());
        }
        Optional<CompletableFuture<R>> reduce = futures.stream().reduce((f0, fi) -> f0.thenCombine(fi, merge));
        return reduce.get().get();
    }


}
