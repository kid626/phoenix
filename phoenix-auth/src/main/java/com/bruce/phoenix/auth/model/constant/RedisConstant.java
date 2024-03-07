package com.bruce.phoenix.auth.model.constant;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName security
 * @Date 2021/12/26 16:24
 * @Author fzh
 */
public class RedisConstant {

    private RedisConstant() {
    }

    public static final String SECRET_KEY = "auth:secret:key:{0}";

    public static final String IMAGE_CAPTCHA_RID = "auth:image:captcha:rid:{0}";

    public static final String LOGIN_RETRY_NUM = "auth:login:retry:{0}";

}
