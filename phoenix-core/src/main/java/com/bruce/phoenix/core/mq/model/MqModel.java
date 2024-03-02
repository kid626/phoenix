package com.bruce.phoenix.core.mq.model;

import com.bruce.phoenix.common.model.constants.CommonConstant;
import com.bruce.phoenix.core.mq.service.IMqService;
import lombok.Data;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/2 14:07
 * @Author Bruce
 */
@Data
public class MqModel<T> {

    private String messageId;

    /**
     * 用来区分不同队列，默认为 queue
     */
    private String topic = "queue";

    private Long errorCount = 1L;

    private Long errorAllowCount = 3L;

    /**
     * 执行器类型，默认为 default
     * {@link IMqService#getType()}
     */
    private String type = CommonConstant.DEFAULT;

    private T params;

}
