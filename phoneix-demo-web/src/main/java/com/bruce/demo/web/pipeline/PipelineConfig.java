package com.bruce.demo.web.pipeline;

import com.bruce.phoenix.core.pipeline.compoent.PipelineComponent;
import com.bruce.phoenix.core.pipeline.process.ProcessTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/5/12 15:12
 * @Author Bruce
 */
@Configuration
public class PipelineConfig {

    @Resource
    private DemoFirstAction demoFirstAction;
    @Resource
    private DemoSecondAction demoSecondAction;
    @Resource
    private DemoThirdAction demoThirdAction;

    /**
     * 第一类执行流程
     * demo1
     * demo2
     */
    @Bean("firstTemplate")
    public ProcessTemplate<String> firstTemplate() {
        ProcessTemplate<String> processTemplate = new ProcessTemplate<>();
        processTemplate.setProcessList(Arrays.asList(demoFirstAction, demoSecondAction));
        return processTemplate;
    }

    /**
     * 第二类执行流程
     * demo1
     * demo2
     * demo3
     */
    @Bean("secondTemplate")
    public ProcessTemplate<String> secondTemplate() {
        ProcessTemplate<String> processTemplate = new ProcessTemplate<>();
        processTemplate.setProcessList(Arrays.asList(demoFirstAction, demoSecondAction, demoThirdAction));
        return processTemplate;
    }

    /**
     * pipeline流程控制器
     */
    @Bean("pipelineComponent")
    public PipelineComponent<String> pipelineComponent() {
        PipelineComponent<String> processController = new PipelineComponent<>();
        Map<String, ProcessTemplate<String>> templateConfig = new HashMap<>(4);
        templateConfig.put("first", firstTemplate());
        templateConfig.put("second", secondTemplate());
        processController.setTemplateConfig(templateConfig);
        return processController;
    }
}
