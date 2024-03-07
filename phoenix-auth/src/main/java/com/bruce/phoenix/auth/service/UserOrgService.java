package com.bruce.phoenix.auth.service;

import com.bruce.phoenix.auth.model.po.UserOrg;
import com.bruce.phoenix.auth.model.form.UserOrgForm;
import com.bruce.phoenix.auth.model.vo.UserOrgVO;
import com.bruce.phoenix.auth.model.query.UserOrgQuery;
import com.bruce.phoenix.common.model.common.PageData;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 用户-组织关系service 层
 * @ProjectName phoenix-auth
 * @Date 2024-03-07
 * @Author Bruce
 */
public interface UserOrgService {

    /**
     * 新增
     *
     * @param form UserOrgForm
     * @return 主键
     */
    Long save(UserOrgForm form);


    /**
     * 更新
     *
     * @param form UserOrgForm
     */
    void update(UserOrgForm form);

    /**
     * 删除
     *
     * @param id 主键
     */
    void remove(Long id);

    /**
     * 按主键查询
     */
    UserOrg queryById(Long id);

    /**
     * 分页查询
     *
     * @param query UserOrgQuery
     * @return 分页信息
     */
    PageData<UserOrgVO> queryByPage(UserOrgQuery query);

}
