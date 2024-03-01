package com.bruce.phoenix.core.event.service;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/1 16:05
 * @Author Bruce
 */
@FunctionalInterface
public interface EventService<T> {


    void proceed(T params);

}
