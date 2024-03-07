package com.bruce.phoenix.auth.service;

import com.bruce.phoenix.auth.model.po.Role;
import com.bruce.phoenix.auth.model.po.UserRole;
import com.bruce.phoenix.auth.model.form.UserRoleForm;
import com.bruce.phoenix.auth.model.vo.UserRoleVO;
import com.bruce.phoenix.auth.model.query.UserRoleQuery;
import com.bruce.phoenix.common.model.common.PageData;

import java.util.List;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc service 层
 * @ProjectName phoenix-auth
 * @Date 2024-03-05
 * @Author Bruce
 */
public interface UserRoleService {

    /**
     * 新增
     *
     * @param form UserRoleForm
     * @return 主键
     */
    Long save(UserRoleForm form);


    /**
     * 更新
     *
     * @param form UserRoleForm
     */
    void update(UserRoleForm form);

    /**
     * 删除
     *
     * @param id 主键
     */
    void remove(Long id);

    /**
     * 按主键查询
     */
    UserRole queryById(Long id);

    /**
     * 分页查询
     *
     * @param query UserRoleQuery
     * @return 分页信息
     */
    PageData<UserRoleVO> queryByPage(UserRoleQuery query);

    /**
     * 根据用户id获取
     *
     * @param userId 用户主键
     * @return 角色集合
     */
    List<Role> queryByUserId(Long userId);
}
