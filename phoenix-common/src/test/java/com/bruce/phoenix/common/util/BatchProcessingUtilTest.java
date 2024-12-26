package com.bruce.phoenix.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.lang.Console;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/12/26 14:02
 * @Author Bruce
 */
public class BatchProcessingUtilTest {

    @Test
    public void batchProcessing() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Long> list = generateList();
        BatchProcessingUtil.batchProcessing(list, 1000, (strings) -> {
            Console.log(strings);
        });
        stopWatch.stop();
        Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }

    @Test
    public void parallelBatchProcessing() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Long> list = generateList();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        BatchProcessingUtil.parallelBatchProcessing(list, 1000, (strings) -> {
            Console.log(strings);
            atomicInteger.getAndIncrement();
        });
        while (atomicInteger.get() < list.size() / 1000) {

        }
        stopWatch.stop();
        Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }

    @Test
    public void batchProcessingWithReturn() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Long> list = generateList();
        Function<List<Long>, Long> action = (input) -> {
            return Long.valueOf(input.size());
        };
        BiFunction<? super Long, ? super Long, ? extends Long> merge = (a, b) -> a + b;
        Long value = BatchProcessingUtil.batchProcessing(list, 1000, action, merge, 0L);
        stopWatch.stop();
        Console.log(value);
        Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }

    @Test
    public void parallelBatchProcessingWithReturn() throws ExecutionException, InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Long> list = generateList();
        Function<List<Long>, Long> action = (input) -> {
            return Long.valueOf(input.size());
        };
        BiFunction<? super Long, ? super Long, ? extends Long> merge = (a, b) -> a + b;
        Long value = BatchProcessingUtil.parallelBatchProcessing(list, 1000, action, merge);
        stopWatch.stop();
        Console.log(value);
        Console.log(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }

    private List<Long> generateList() {
        List<Long> list = CollUtil.newArrayList();
        for (int i = 0; i < 1000000; i++) {
            list.add(Long.valueOf(i));
        }
        return list;
    }

}
