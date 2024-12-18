package com.bruce.phoenix.common.model.structure;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc 自定义优先级队列
 * @ProjectName phoenix
 * @Date 2024/12/17 20:45
 * @Author Bruce
 */
public class CustomPriorityQueue<E> extends PriorityQueue<E> {

    private Integer max;

    public CustomPriorityQueue(Comparator<E> comparator, Integer max) {
        super(comparator);
        this.max = max;
    }

    @Override
    public boolean offer(E e) {
        boolean result = super.offer(e);
        if (this.size() > max) {
            this.poll();
        }
        return result;
    }
}
