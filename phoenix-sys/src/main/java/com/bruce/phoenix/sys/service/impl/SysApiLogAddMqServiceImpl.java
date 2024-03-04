package com.bruce.phoenix.sys.service.impl;

import com.bruce.phoenix.core.mq.service.IMqService;
import com.bruce.phoenix.sys.model.constant.SysConstant;
import com.bruce.phoenix.sys.model.form.SysApiLogForm;
import com.bruce.phoenix.sys.service.SysApiLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/2 17:00
 * @Author Bruce
 */
@Slf4j
@Service
public class SysApiLogAddMqServiceImpl implements IMqService<SysApiLogForm> {

    @Resource
    private SysApiLogService sysApiLogService;


    @Override
    public String getType() {
        return SysConstant.SYS_API_LOG_ADD_MQ_TYPE;
    }

    @Override
    public void proceed(SysApiLogForm form) {
        sysApiLogService.save(form);
    }
}
