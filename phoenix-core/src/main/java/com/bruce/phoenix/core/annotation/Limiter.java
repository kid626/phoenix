package com.bruce.phoenix.core.annotation;

import java.lang.annotation.*;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc 限流插件
 * @ProjectName phoenix
 * @Date 2024/2/29 15:46
 * @Author Bruce
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Limiter {

    // 唯一键值,会拼上参数
    String key() default "limit:";

    // 降级,信息提示
    String message() default "资源被占用";

    // 过期时间,单位毫秒
    long expire() default 60000;

    // 限制次数
    int count() default 1;


}
