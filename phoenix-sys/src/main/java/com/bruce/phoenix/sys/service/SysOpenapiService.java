package com.bruce.phoenix.sys.service;

import com.bruce.phoenix.sys.model.po.SysOpenapi;
import com.bruce.phoenix.sys.model.form.SysOpenapiForm;
import com.bruce.phoenix.sys.model.vo.SysOpenapiVO;
import com.bruce.phoenix.sys.model.query.SysOpenapiQuery;
import com.bruce.phoenix.common.model.common.PageData;

/**
 * @Copyright Copyright © 2025 Bruce . All rights reserved.
 * @Desc service 层
 * @ProjectName phoenix-sys
 * @Date 2025-06-27
 * @Author Bruce
 */
public interface SysOpenapiService {

    /**
     * 新增
     *
     * @param form SysOpenapiForm
     * @return 主键
     */
    SysOpenapi save(SysOpenapiForm form);


    /**
     * 更新
     *
     * @param form SysOpenapiForm
     */
    void update(SysOpenapiForm form);

    /**
     * 删除
     *
     * @param id 主键
     */
    void remove(Long id);

    /**
     * 按主键查询
     */
    SysOpenapi queryById(Long id);

    /**
     * 分页查询
     *
     * @param query SysOpenapiQuery
     * @return 分页信息
     */
    PageData<SysOpenapiVO> queryByPage(SysOpenapiQuery query);

    /**
     * 根据 appKey 查询
     */
    SysOpenapi queryByAccessKey(String appKey);
}
