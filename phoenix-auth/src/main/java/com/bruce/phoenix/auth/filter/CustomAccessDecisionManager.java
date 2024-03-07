package com.bruce.phoenix.auth.filter;

import com.bruce.phoenix.auth.model.constant.AuthConstant;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName security
 * @Date 2021/12/25 14:44
 * @Author fzh
 */
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (null != configAttributes && !configAttributes.isEmpty()) {
            for (ConfigAttribute configAttribute : configAttributes) {
                String needRole = configAttribute.getAttribute();
                if (AuthConstant.DEFAULT_RESOURCE.equals(needRole)) {
                    // 默认资源直接放过，只要登录即可
                    return;
                }
                for (GrantedAuthority ga : authentication.getAuthorities()) {
                    if (ga.getAuthority().equals(needRole)) {
                        return;
                    }
                }
            }
        }
        throw new AccessDeniedException("没有权限!");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
