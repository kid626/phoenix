package com.bruce.phoenix.auth.config;

import com.bruce.phoenix.auth.component.JwtTokenComponent;
import com.bruce.phoenix.auth.component.RedisTokenComponent;
import com.bruce.phoenix.auth.component.TokenComponent;
import com.bruce.phoenix.auth.filter.AuthenticationFilter;
import com.bruce.phoenix.auth.filter.CustomAccessDecisionManager;
import com.bruce.phoenix.auth.filter.CustomFilterSecurityInterceptor;
import com.bruce.phoenix.auth.filter.CustomSecurityMetadataSource;
import com.bruce.phoenix.auth.handler.CustomAccessDeniedHandler;
import com.bruce.phoenix.auth.handler.CustomAuthenticationEntryPoint;
import com.bruce.phoenix.auth.model.enums.TokenType;
import com.bruce.phoenix.auth.service.AuthService;
import com.bruce.phoenix.core.component.middleware.RedisComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/5 15:20
 * @Author Bruce
 */
@Configuration
public class AuthConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private AuthProperty property;
    @Resource
    private AuthService authService;
    @Resource
    private CustomSecurityMetadataSource customSecurityMetadataSource;
    @Resource
    private RedisComponent redisComponent;
    @Resource
    private CustomSecurityMetadataSource securityMetadataSource;
    @Resource
    private CustomAccessDecisionManager myAccessDecisionManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (!Boolean.TRUE.equals(property.getEnable())) {
            http.authorizeRequests(authorizeRequests ->
                    // 所有请求均放过, spring security 就没有什么用了
                    // anyRequest() 限定任意的请求
                    // permitAll() 无条件允许访问
                    authorizeRequests.anyRequest().permitAll()
            );
        } else {
            http
                    .exceptionHandling()
                    .accessDeniedHandler(new CustomAccessDeniedHandler())
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                    .and()
                    .cors()
                    .disable()
                    .csrf()
                    .disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .addFilter(new AuthenticationFilter(authenticationManager(), authService,
                            tokenComponent(authService, redisComponent)))
                    .addFilterBefore(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class)
                    .httpBasic();
        }
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        List<String> list = new ArrayList<>(Arrays.asList(property.getDefaultExcludeUrls()));
        if (!property.isActiveProfile("prod")) {
            list.addAll(Arrays.asList(property.getSwaggerUrls()));
        }
        if (property.getExcludeUrls() != null) {
            list.addAll(Arrays.asList(property.getExcludeUrls()));
        }
        web.ignoring().antMatchers(list.toArray(new String[]{}));
    }

    private CustomFilterSecurityInterceptor customFilterSecurityInterceptor() {
        return new CustomFilterSecurityInterceptor(securityMetadataSource, myAccessDecisionManager);
    }

    @Bean
    public TokenComponent tokenComponent(AuthService authService, RedisComponent redisComponent) {
        AuthProperty.TokenManager tokenManager = property.getToken();
        if (tokenManager == null) {
            throw new IllegalArgumentException("token 不能为空!");
        }
        TokenType tokenType = tokenManager.getType();
        if (tokenType == null) {
            throw new IllegalArgumentException("token.type 不能为空!");
        }
        if (tokenType == TokenType.JWT) {
            return new JwtTokenComponent(authService, tokenManager);
        }
        if (tokenType == TokenType.REDIS) {
            return new RedisTokenComponent(authService, redisComponent, tokenManager);
        }
        throw new IllegalArgumentException("token.type 不支持!");
    }

    @Scheduled(initialDelay = 5 * 60 * 1000, fixedDelay = 5 * 60 * 1000)
    public void refreshResources() {
        customSecurityMetadataSource.refreshResources();
    }
}
