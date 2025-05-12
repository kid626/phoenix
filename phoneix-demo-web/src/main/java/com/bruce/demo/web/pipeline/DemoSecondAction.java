package com.bruce.demo.web.pipeline;

import com.bruce.phoenix.core.pipeline.model.ProcessContextModel;
import com.bruce.phoenix.core.pipeline.process.BusinessProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/5/12 15:06
 * @Author Bruce
 */
@Slf4j
@Service
public class DemoSecondAction implements BusinessProcess<String> {

    @Override
    public void process(ProcessContextModel<String> context) {
        log.info("[DemoSecondAction#process] context:{}", context);
        context.setResponse("third");
        // 退出条件
        context.setNeedBreak(true);
    }

}
