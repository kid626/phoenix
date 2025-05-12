package com.bruce.phoenix.core.pipeline.compoent;

import cn.hutool.core.collection.CollUtil;
import com.bruce.phoenix.common.exception.CommonException;
import com.bruce.phoenix.core.pipeline.model.ProcessContextModel;
import com.bruce.phoenix.core.pipeline.process.BusinessProcess;
import com.bruce.phoenix.core.pipeline.process.ProcessTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/5/12 14:03
 * @Author Bruce
 */
public class PipelineComponent<T> {

    /**
     * 模板映射
     */
    private Map<String, ProcessTemplate<T>> templateConfig = new HashMap<>(1);

    public void setTemplateConfig(Map<String, ProcessTemplate<T>> templateConfig) {
        this.templateConfig = templateConfig;
    }

    public Map<String, ProcessTemplate<T>> getTemplateConfig() {
        return this.templateConfig;
    }

    /**
     * 执行责任链
     *
     * @param context 上下文
     * @return 返回上下文内容
     */
    public ProcessContextModel<T> process(ProcessContextModel<T> context) {

        // 前置检查
        try {
            preCheck(context);
        } catch (CommonException e) {
            context.setResponse(e.getMessage());
            return context;
        }

        // 遍历流程节点
        List<BusinessProcess<T>> processList = templateConfig.get(context.getCode()).getProcessList();
        for (BusinessProcess<T> businessProcess : processList) {
            businessProcess.process(context);
            if (Boolean.TRUE.equals(context.getNeedBreak())) {
                break;
            }
        }
        return context;
    }


    /**
     * 执行前检查，出错则抛出异常
     *
     * @param context 执行上下文
     */
    private void preCheck(ProcessContextModel<T> context) throws CommonException {
        // 上下文
        if (Objects.isNull(context)) {
            throw new CommonException("流程上下文为空!");
        }

        // 业务代码
        String businessCode = context.getCode();
        if (Objects.isNull(businessCode)) {
            throw new CommonException("业务代码为空!");
        }

        // 执行模板
        ProcessTemplate<T> processTemplate = templateConfig.get(businessCode);
        if (Objects.isNull(processTemplate)) {
            throw new CommonException("流程模板配置为空!");
        }

        // 执行模板列表
        List<BusinessProcess<T>> processList = processTemplate.getProcessList();
        if (CollUtil.isEmpty(processList)) {
            throw new CommonException("业务处理器配置为空!");
        }

    }

}
