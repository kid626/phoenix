package com.bruce.phoenix.core.pipeline.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc 责任链上下文
 * @ProjectName phoenix
 * @Date 2025/5/12 14:04
 * @Author Bruce
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessContextModel<T> implements Serializable {

    /**
     * 标识责任链的code
     */
    private String code;
    /**
     * 存储责任链上下文数据的模型
     */
    private T processModel;
    /**
     * 责任链中断的标识
     */
    private Boolean needBreak;
    /**
     * 流程处理的结果
     */
    private String response;
}
