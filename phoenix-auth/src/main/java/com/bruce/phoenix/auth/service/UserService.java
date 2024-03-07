package com.bruce.phoenix.auth.service;

import com.bruce.phoenix.auth.model.po.User;
import com.bruce.phoenix.auth.model.form.UserForm;
import com.bruce.phoenix.auth.model.vo.UserVO;
import com.bruce.phoenix.auth.model.query.UserQuery;
import com.bruce.phoenix.common.model.common.PageData;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc service 层
 * @ProjectName phoenix-auth
 * @Date 2024-03-05
 * @Author Bruce
 */
public interface UserService {

    /**
     * 新增
     *
     * @param form UserForm
     * @return 主键
     */
    Long save(UserForm form);


    /**
     * 更新
     *
     * @param form UserForm
     */
    void update(UserForm form);

    /**
     * 删除
     *
     * @param id 主键
     */
    void remove(Long id);

    /**
     * 按主键查询
     */
    User queryById(Long id);

    /**
     * 分页查询
     *
     * @param query UserQuery
     * @return 分页信息
     */
    PageData<UserVO> queryByPage(UserQuery query);

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return User
     */
    User queryByUsername(String username);
}
