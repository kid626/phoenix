package com.bruce.phoenix.auth.filter;

import com.bruce.phoenix.auth.component.TokenComponent;
import com.bruce.phoenix.auth.service.AuthService;
import com.bruce.phoenix.core.model.security.UserAuthentication;
import com.bruce.phoenix.core.util.UserUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Copyright Copyright © 2020 fanzh . All rights reserved.
 * @Desc
 * @ProjectName security
 * @Date 2020/12/25 9:31
 * @Author Bruce
 */
public class AuthenticationFilter extends BasicAuthenticationFilter {

    private final AuthService authService;
    private final TokenComponent tokenComponent;

    public AuthenticationFilter(AuthenticationManager authenticationManager, AuthService authService, TokenComponent tokenComponent) {
        super(authenticationManager);
        this.authService = authService;
        this.tokenComponent = tokenComponent;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = tokenComponent.getToken(request);
        UserAuthentication authentication = authService.login(token);
        if (authentication != null) {
            UserUtil.setCurrentUser(authentication);
        }
        chain.doFilter(request, response);
    }

}
