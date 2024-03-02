package com.bruce.phoenix.core.mq.service;

import com.bruce.phoenix.common.model.constants.CommonConstant;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/2 14:07
 * @Author Bruce
 */
public interface IMqService<T> {


    default String getType() {
        return CommonConstant.DEFAULT;
    }

    /**
     * 处理事件
     *
     * @param params 参数
     */
    void proceed(T params);

}
