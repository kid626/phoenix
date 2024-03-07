package com.bruce.phoenix.auth.config;

import com.bruce.phoenix.auth.model.enums.TokenType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/5 15:16
 * @Author Bruce
 */
@Component
@ConfigurationProperties(prefix = AuthProperty.PREFIX)
@Data
public class AuthProperty implements EnvironmentAware {

    public static final String PREFIX = "auth";

    private String[] excludeUrls;

    private String[] defaultExcludeUrls = new String[]{"/auth/login", "/auth/secretKey", "/auth/showResScript",
            "/auth/refresh", "/auth/images/captcha", "/auth/redirect", "/error"};

    private String[] swaggerUrls = new String[]{"/doc.html", "/v2/api-docs", "/v2/api-docs-ext",
            "/swagger-resources", "/webjars/**"};

    private String[] activeProfiles;

    private Environment env;

    private Boolean enable;

    private TokenManager token = new TokenManager();

    private CaptchaManager captcha = new CaptchaManager();

    private RetryManager retry = new RetryManager();


    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
        this.activeProfiles = environment.getActiveProfiles();
    }


    @Data
    public static class TokenManager {

        private TokenType type = TokenType.REDIS;

        private long expire = 24 * 60 * 60 * 1000;

        private String name = "token";

        private String secret;

    }

    @Data
    public static class CaptchaManager {

        private Boolean enable = false;

        private long expire = 2 * 60;

        private int length = 16;

        private int lineCount = 100;

        private int codeCount = 4;

        private String name = "X-Rid";

    }

    @Data
    public static class RetryManager {

        private long expire = 24 * 60 * 60;

        private int nums = 5;

    }

    public boolean isActiveProfile(String profile) {
        for (String activeProfile : this.getActiveProfiles()) {
            if (activeProfile.equals(profile)) {
                return true;
            }
        }
        return false;
    }
}
