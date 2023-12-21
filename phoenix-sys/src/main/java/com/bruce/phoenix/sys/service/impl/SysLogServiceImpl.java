package com.bruce.phoenix.sys.service.impl;

import com.bruce.phoenix.common.model.enums.YesOrNoEnum;
import com.bruce.phoenix.sys.mapper.SysLogMapper;
import com.bruce.phoenix.sys.model.form.SysLogForm;
import com.bruce.phoenix.sys.model.po.SysLog;
import com.bruce.phoenix.sys.service.SysLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Copyright Copyright © 2022 Bruce . All rights reserved.
 * @Desc 系统日志记录service 实现类
 * @ProjectName demo
 * @Date 2022-6-20 9:50:39
 * @Author Bruce
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogMapper mapper;

    @Override
    public void log(SysLogForm form) {
        SysLog po = new SysLog();
        BeanUtils.copyProperties(form, po);
        po.setCreateTime(new Date());
        po.setIsDeleted(YesOrNoEnum.NO.getCode());
        mapper.insert(po);
    }
}
