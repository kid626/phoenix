package com.bruce.phoenix.auth.component;

import com.alibaba.fastjson.JSONObject;
import com.bruce.phoenix.auth.config.AuthProperty;
import com.bruce.phoenix.auth.service.AuthService;
import com.bruce.phoenix.core.component.middleware.RedisComponent;
import com.bruce.phoenix.core.model.security.AuthUser;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName security
 * @Date 2021/12/25 9:37
 * @Author fzh
 */
public class RedisTokenComponent implements TokenComponent {

    private final AuthService authService;

    private final RedisComponent redisComponent;

    private final AuthProperty.TokenManager tokenManager;

    public RedisTokenComponent(AuthService authService, RedisComponent redisComponent, AuthProperty.TokenManager tokenManager) {
        this.authService = authService;
        this.redisComponent = redisComponent;
        this.tokenManager = tokenManager;
    }

    @Override
    public String createToken(String username) {
        String token = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        AuthUser user = authService.queryByUsername(username);
        redisComponent.setExpire(token, JSONObject.toJSONString(user), tokenManager.getExpire(), TimeUnit.MILLISECONDS);
        return token;
    }

    @Override
    public String getFromToken(String token) {
        return redisComponent.get(token);
    }

    @Override
    public void removeToken(String token) {
        redisComponent.deleteKey(token);
    }

    @Override
    public String getToken(HttpServletRequest request) {
        String tokenName = tokenManager.getName();
        //step1:尝试从url参数获取
        String token = request.getParameter(tokenName);
        //step2:尝试从header获取
        if (StringUtils.isEmpty(token)) {
            token = request.getHeader(tokenName);
        }
        //step3:从cookie获取
        if (StringUtils.isEmpty(token)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(tokenName)) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }
        return token;
    }

}
