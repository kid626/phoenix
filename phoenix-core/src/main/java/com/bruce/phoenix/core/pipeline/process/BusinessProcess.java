package com.bruce.phoenix.core.pipeline.process;

import com.bruce.phoenix.core.pipeline.model.ProcessContextModel;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc 业务执行器
 * @ProjectName phoenix
 * @Date 2025/5/12 14:03
 * @Author Bruce
 */
public interface BusinessProcess<T> {

    /**
     * 真正处理逻辑
     *
     * @param context
     */
    void process(ProcessContextModel<T> context);
}
