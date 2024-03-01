package com.bruce.phoenix.sys.service;

import com.bruce.phoenix.common.model.common.PageData;
import com.bruce.phoenix.sys.model.form.SysApiLogForm;
import com.bruce.phoenix.sys.model.po.SysApiLog;
import com.bruce.phoenix.sys.model.query.SysApiLogQuery;
import com.bruce.phoenix.sys.model.vo.SysApiLogVO;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 系统第三方请求日志service 层
 * @ProjectName phoenix-sys
 * @Date 2024-03-01
 * @Author Bruce
 */
public interface SysApiLogService {

    /**
     * 新增
     *
     * @param form SysApiLogForm
     * @return 主键
     */
    // @Async
    Long save(SysApiLogForm form);


    /**
     * 根据requestId更新
     *
     * @param form SysApiLogForm
     */
    // @Async
    void update(SysApiLogForm form);

    /**
     * 删除
     *
     * @param id 主键
     */
    void remove(Long id);

    /**
     * 按主键查询
     */
    SysApiLog queryById(Long id);

    /**
     * 分页查询
     *
     * @param query SysApiLogQuery
     * @return 分页信息
     */
    PageData<SysApiLogVO> queryByPage(SysApiLogQuery query);

}
