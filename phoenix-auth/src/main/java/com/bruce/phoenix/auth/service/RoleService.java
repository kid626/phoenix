package com.bruce.phoenix.auth.service;

import com.bruce.phoenix.auth.model.po.Role;
import com.bruce.phoenix.auth.model.form.RoleForm;
import com.bruce.phoenix.auth.model.vo.RoleVO;
import com.bruce.phoenix.auth.model.query.RoleQuery;
import com.bruce.phoenix.common.model.common.PageData;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc service 层
 * @ProjectName phoenix-auth
 * @Date 2024-03-05
 * @Author Bruce
 */
public interface RoleService {

    /**
     * 新增
     *
     * @param form RoleForm
     * @return 主键
     */
    Long save(RoleForm form);


    /**
     * 更新
     *
     * @param form RoleForm
     */
    void update(RoleForm form);

    /**
     * 删除
     *
     * @param id 主键
     */
    void remove(Long id);

    /**
     * 按主键查询
     */
    Role queryById(Long id);

    /**
     * 分页查询
     *
     * @param query RoleQuery
     * @return 分页信息
     */
    PageData<RoleVO> queryByPage(RoleQuery query);

}
