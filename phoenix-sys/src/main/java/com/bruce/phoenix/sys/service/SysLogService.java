package com.bruce.phoenix.sys.service;

import com.bruce.phoenix.common.model.common.PageData;
import com.bruce.phoenix.sys.model.po.SysLog;
import com.bruce.phoenix.sys.model.query.SysLogQuery;
import com.bruce.phoenix.sys.model.vo.SysLogVO;

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
     * @param po SysLog
     */
    void log(SysLog po);

    /**
     * 分页查询
     *
     * @param query SysLogQuery
     * @return  SysLogVO
     */
    PageData<SysLogVO> queryByPage(SysLogQuery query);
}
