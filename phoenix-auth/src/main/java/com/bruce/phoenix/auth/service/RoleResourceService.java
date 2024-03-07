package com.bruce.phoenix.auth.service;

import com.bruce.phoenix.auth.model.po.RoleResource;
import com.bruce.phoenix.auth.model.form.RoleResourceForm;
import com.bruce.phoenix.auth.model.vo.RoleResourceVO;
import com.bruce.phoenix.auth.model.query.RoleResourceQuery;
import com.bruce.phoenix.common.model.common.PageData;
import com.bruce.phoenix.core.model.security.AuthResource;

import java.util.List;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc service 层
 * @ProjectName phoenix-auth
 * @Date 2024-03-05
 * @Author Bruce
 */
public interface RoleResourceService {

    /**
     * 新增
     *
     * @param form RoleResourceForm
     * @return 主键
     */
    Long save(RoleResourceForm form);


    /**
     * 更新
     *
     * @param form RoleResourceForm
     */
    void update(RoleResourceForm form);

    /**
     * 删除
     *
     * @param id 主键
     */
    void remove(Long id);

    /**
     * 按主键查询
     */
    RoleResource queryById(Long id);

    /**
     * 分页查询
     *
     * @param query RoleResourceQuery
     * @return 分页信息
     */
    PageData<RoleResourceVO> queryByPage(RoleResourceQuery query);

    /**
     * 根据角色主键获取
     *
     * @param roleId    角色主键
     * @return  AuthResource
     */
    List<AuthResource> queryByRoleId(Long roleId);
}
