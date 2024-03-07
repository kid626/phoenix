package com.bruce.demo.web.service.impl;

import com.bruce.demo.web.model.constant.DemoConstant;
import com.bruce.phoenix.core.mq.model.BaseMqModel;
import com.bruce.phoenix.core.mq.service.IMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/2 15:43
 * @Author Bruce
 */
@Slf4j
@Service
public class DemoIMqServiceImpl implements IMqService<BaseMqModel> {

    @Override
    public String getType() {
        return DemoConstant.MQ_TYPE_NAME_DEMO;
    }

    @Override
    public void proceed(BaseMqModel params) {
        log.info("hello {}", params.getMessageId());
    }
}
