package com.bruce.phoenix.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.bruce.phoenix.auth.component.AuthComponent;
import com.bruce.phoenix.auth.component.TokenComponent;
import com.bruce.phoenix.auth.config.AuthProperty;
import com.bruce.phoenix.auth.model.constant.RedisConstant;
import com.bruce.phoenix.auth.model.dto.LoginDTO;
import com.bruce.phoenix.auth.model.enums.ResourceTypeEnum;
import com.bruce.phoenix.auth.model.po.Resource;
import com.bruce.phoenix.auth.model.po.Role;
import com.bruce.phoenix.auth.model.po.User;
import com.bruce.phoenix.auth.model.vo.ResourceVO;
import com.bruce.phoenix.auth.service.*;
import com.bruce.phoenix.common.util.EncryptUtil;
import com.bruce.phoenix.core.model.security.AuthResource;
import com.bruce.phoenix.core.model.security.AuthUser;
import com.bruce.phoenix.core.model.security.UserAuthentication;
import com.bruce.phoenix.core.util.UserUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/6 18:15
 * @Author Bruce
 */
@Service
public class LocalAuthService implements AuthService {

    @javax.annotation.Resource
    private UserService userService;
    @javax.annotation.Resource
    private ResourceService resourceService;
    @javax.annotation.Resource
    private TokenComponent tokenComponent;
    @javax.annotation.Resource
    private UserRoleService userRoleService;
    @javax.annotation.Resource
    private RoleResourceService roleResourceService;
    @javax.annotation.Resource
    private AuthComponent authComponent;
    @javax.annotation.Resource
    private AuthProperty property;

    @Override
    public UserAuthentication login(LoginDTO loginDTO) {
        AuthProperty.CaptchaManager captcha = property.getCaptcha();
        AuthProperty.RetryManager retryManager = property.getRetry();
        if (captcha != null && Boolean.TRUE.equals(captcha.getEnable())) {
            authComponent.checkCaptchaAndDelete(loginDTO.getRid(), loginDTO.getCode());
        }
        AuthUser user = queryByUsername(loginDTO.getUsername());
        if (user == null || !user.getPassword().equals(EncryptUtil.md5(loginDTO.getPassword(), user.getSalt()))) {
            authComponent.incrExpireAndThrow(MessageFormat.format(RedisConstant.LOGIN_RETRY_NUM,
                    loginDTO.getUsername()), retryManager);
        }
        UserAuthentication userAuthentication = new UserAuthentication();
        BeanUtils.copyProperties(Objects.requireNonNull(user), userAuthentication);
        String token = tokenComponent.createToken(loginDTO.getUsername());
        userAuthentication.setToken(token);
        List<AuthResource> list = queryByUserId(user.getId());
        userAuthentication.setAuthorities(list);
        return userAuthentication;
    }

    @Override
    public UserAuthentication login(String token) {
        if (StrUtil.isEmpty(token)) {
            return null;
        }
        String value = tokenComponent.getFromToken(token);
        if (StrUtil.isEmpty(value)) {
            return null;
        }
        AuthUser authUser = JSONObject.parseObject(value, AuthUser.class);
        // 刷新用户信息
        User user = userService.queryById(authUser.getId());
        BeanUtils.copyProperties(user, authUser);
        UserAuthentication userAuthentication = new UserAuthentication();
        BeanUtils.copyProperties(authUser, userAuthentication);
        List<AuthResource> list = queryByUserId(authUser.getId());
        userAuthentication.setToken(token);
        userAuthentication.setAuthorities(list);
        return userAuthentication;
    }

    @Override
    public void logout() {
        String token = UserUtil.getCurrentUser().getToken();
        UserUtil.clearCurrentUser();
        tokenComponent.removeToken(token);
    }

    @Override
    public AuthUser queryByUsername(String username) {
        User user = userService.queryByUsername(username);
        AuthUser authUser = new AuthUser();
        BeanUtils.copyProperties(user, authUser);
        return authUser;
    }

    @Override
    public List<AuthResource> queryByUserId(Long userId) {
        List<AuthResource> result = new ArrayList<>();
        List<Role> roleList = userRoleService.queryByUserId(userId);
        for (Role role : roleList) {
            List<AuthResource> resourceList = roleResourceService.queryByRoleId(role.getId());
            result.addAll(resourceList);
        }
        return result;
    }

    @Override
    public List<String> permList() {
        List<AuthResource> list = (List<AuthResource>) UserUtil.getCurrentUser().getAuthorities();
        return list.stream().map(AuthResource::getCode).collect(Collectors.toList());
    }

    @Override
    public List<AuthResource> getAvailableResources() {
        List<Resource> availableResources = resourceService.getAvailableResources();
        return availableResources.stream().map(resourceDTO -> {
            AuthResource resource = new AuthResource();
            BeanUtils.copyProperties(resourceDTO, resource);
            return resource;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ResourceVO> tree() {
        List<AuthResource> list = (List<AuthResource>) UserUtil.getCurrentUser().getAuthorities();
        List<ResourceVO> all =
                list.stream().filter(authResource -> ResourceTypeEnum.MENU.getCode().equals(authResource.getType()))
                        .map(resource -> {
                            ResourceVO vo = new ResourceVO();
                            BeanUtils.copyProperties(resource, vo);
                            return vo;
                        }).collect(Collectors.toList());
        //操作所有菜单数据
        Map<String, List<ResourceVO>> groupMap = all.stream().collect(Collectors.groupingBy(ResourceVO::getParentCode));
        all.forEach(resourceVO -> {
            resourceVO.setChildren(groupMap.get(resourceVO.getCode()));
            resourceVO.setHasChild(CollectionUtils.isEmpty(resourceVO.getChildren()));
        });
        return all.stream().filter(resourceVO -> StringUtils.isBlank(resourceVO.getParentCode())).collect(Collectors.toList());
    }
}
