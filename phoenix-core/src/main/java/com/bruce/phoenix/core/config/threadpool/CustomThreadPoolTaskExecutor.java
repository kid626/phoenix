package com.bruce.phoenix.core.config.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName thread-pool-demo
 * @Date 2023/3/22 14:22
 * @Author fzh
 */
@Slf4j
public class CustomThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {


    @Override
    public void execute(Runnable task) {
        super.execute(task);
        logThreadPoolStatus();
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        super.execute(task, startTimeout);
        logThreadPoolStatus();
    }

    @Override
    public Future<?> submit(Runnable task) {
        Future<?> future = super.submit(task);
        logThreadPoolStatus();
        return future;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        Future<T> future = super.submit(task);
        logThreadPoolStatus();
        return future;
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        ListenableFuture<?> listenableFuture = super.submitListenable(task);
        logThreadPoolStatus();
        return listenableFuture;
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        ListenableFuture<T> listenableFuture = super.submitListenable(task);
        logThreadPoolStatus();
        return listenableFuture;
    }

    /**
     * 在线程池运行的时候输出线程池的基本信息
     */
    private void logThreadPoolStatus() {
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
        log.info("{},taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                this.getThreadNamePrefix(),
                threadPoolExecutor.getTaskCount(),
                threadPoolExecutor.getCompletedTaskCount(),
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getQueue().size());
        this.showMemoryInfo();
        this.showSystem();
    }

    /**
     * Java 虚拟机的内存系统
     * <p>
     * init约等于xms的值，max约等于xmx的值
     * used是已经被使用的内存大小，committed是当前可使用的内存大小（包括已使用的）
     * committed >= used。committed不足时jvm向系统申请，若超过max则发生OutOfMemoryError错误
     * 机器内存小于192M,堆内存最大为二分之一
     * 机器内存大于等于1024M，堆内存最大为四分之一
     * JVM初始分配的内存由-Xms指定，默认是物理内存的1/64；
     * JVM最大分配的内存由-Xmx指定，默认是物理内存的1/4。
     * 默认空余堆内存小于40%时，JVM就会增大堆直到-Xmx的最大限制；
     * 空余堆内存大于70%时，JVM会减少堆直到-Xms的最小限制。
     * 因此服务器一般设置-Xms、-Xmx相等以避免在每次GC后调整堆的大小。
     * </p>
     */
    private void showMemoryInfo() {
        MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = mem.getHeapMemoryUsage();
        log.info("Heap committed:" + heap.getCommitted() / 1024 / 1024
                + "MB, init:" + heap.getInit() / 1024 / 1024
                + "MB, max:" + heap.getMax() / 1024 / 1024
                + "MB, used:" + heap.getUsed() / 1024 / 1024 + "MB");
    }

    /**
     * Java 虚拟机在其上运行的操作系统
     */
    private void showSystem() {
        OperatingSystemMXBean op = ManagementFactory.getOperatingSystemMXBean();
        log.info("架构:[{}],处理器数:[{}],操作系统:[{}],系统版本:[{}]", op.getArch(), op.getAvailableProcessors(), op.getName(), op.getVersion());
    }


}
