package com.bruce.phoenix.auth.service;

import com.bruce.phoenix.auth.model.dto.LoginDTO;
import com.bruce.phoenix.auth.model.vo.ResourceVO;
import com.bruce.phoenix.core.model.security.AuthResource;
import com.bruce.phoenix.core.model.security.AuthUser;
import com.bruce.phoenix.core.model.security.UserAuthentication;

import java.util.List;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/5 16:20
 * @Author Bruce
 */
public interface AuthService {


    /**
     * 登录校验
     *
     * @param loginDTO LoginDTO
     * @return UserAuthentication
     */
    UserAuthentication login(LoginDTO loginDTO);

    /**
     * token登录校验
     *
     * @param token token
     * @return UserAuthentication
     */
    UserAuthentication login(String token);

    /**
     * 登出
     */
    void logout();

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return 用户
     */
    AuthUser queryByUsername(String username);


    /**
     * 根据用户主键获取所有资源
     *
     * @param userId 用户主键
     * @return 所有权限资源
     */
    List<AuthResource> queryByUserId(Long userId);

    /**
     * 获取资源列表
     *
     * @return 当前用户资源列表
     */
    List<String> permList();

    /**
     * 获取所有按钮资源
     *
     * @return 所有按钮资源
     */
    List<AuthResource> getAvailableResources();

    /**
     * 获取权限树
     *
     * @return 树状结构
     */
    List<ResourceVO> tree();
}
