package com.bruce.phoenix.auth.service;

import com.bruce.phoenix.auth.model.po.Org;
import com.bruce.phoenix.auth.model.form.OrgForm;
import com.bruce.phoenix.auth.model.vo.OrgVO;
import com.bruce.phoenix.auth.model.query.OrgQuery;
import com.bruce.phoenix.common.model.common.PageData;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 组织service 层
 * @ProjectName phoenix-auth
 * @Date 2024-03-07
 * @Author Bruce
 */
public interface OrgService {

    /**
     * 新增
     *
     * @param form OrgForm
     * @return 主键
     */
    Long save(OrgForm form);


    /**
     * 更新
     *
     * @param form OrgForm
     */
    void update(OrgForm form);

    /**
     * 删除
     *
     * @param id 主键
     */
    void remove(Long id);

    /**
     * 按主键查询
     */
    Org queryById(Long id);

    /**
     * 分页查询
     *
     * @param query OrgQuery
     * @return 分页信息
     */
    PageData<OrgVO> queryByPage(OrgQuery query);

}
