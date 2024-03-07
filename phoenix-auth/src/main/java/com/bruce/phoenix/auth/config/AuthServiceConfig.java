package com.bruce.phoenix.auth.config;

import com.bruce.phoenix.auth.service.AuthService;
import com.bruce.phoenix.auth.service.ResourceService;
import com.bruce.phoenix.auth.service.UserService;
import com.bruce.phoenix.auth.service.impl.LocalAuthService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/5 15:20
 * @Author Bruce
 */
@Configuration
public class AuthServiceConfig {


    @Bean
    @ConditionalOnMissingBean(value = AuthService.class, ignored = {LocalAuthService.class})
    public AuthService authService() {
        return new LocalAuthService();
    }

}
