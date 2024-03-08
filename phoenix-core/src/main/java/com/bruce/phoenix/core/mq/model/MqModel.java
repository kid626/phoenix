package com.bruce.phoenix.core.mq.model;

import cn.hutool.core.date.DateUtil;
import com.bruce.phoenix.common.model.constants.CommonConstant;
import com.bruce.phoenix.core.mq.service.IMqService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/2 14:07
 * @Author Bruce
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MqModel<T> implements Serializable {



    private Long errorCount = 1L;
    /**
     * 参数类型，用于序列化 反序列化的时候类型转换
     */
    private String paramsType;

    /**
     * 是否是周期任务
     */
    private Boolean scheduled = Boolean.FALSE;

    /**
     * 开始日期，周期任务必填
     */
    private Date startDate = DateUtil.date();

    /**
     * 执行周期
     * 负数表示只执行一次   单位毫秒
     */
    private Long period = -1L;

    /**
     * 消息主键 自行控制唯一性,建议添加前缀
     */
    private String messageId;

    /**
     * 用来区分不同队列，默认为 queue
     */
    private String topic = "queue";

    private Long errorAllowCount = 3L;

    /**
     * 执行器类型，默认为 default
     * 采用策略模式
     * {@link IMqService#getType()}
     */
    private String type = CommonConstant.DEFAULT;

    private T params;

}
