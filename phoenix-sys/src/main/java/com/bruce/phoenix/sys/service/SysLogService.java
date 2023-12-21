package com.bruce.phoenix.sys.service;

import com.bruce.phoenix.sys.model.form.SysLogForm;

/**
 * @Copyright Copyright © 2022 Bruce . All rights reserved.
 * @Desc 系统日志记录service 层
 * @ProjectName demo
 * @Date 2022-6-20 9:50:39
 * @Author Bruce
 */
public interface SysLogService {

    /**
     * 日志记录
     *
     * @param form  SysLogForm
     */
    void log(SysLogForm form);

}
