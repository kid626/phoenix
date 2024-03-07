package com.bruce.phoenix.auth.scanner;

import com.bruce.phoenix.auth.model.constant.AuthConstant;
import com.bruce.phoenix.auth.model.enums.MethodEnum;

import java.lang.annotation.*;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/7 10:23
 * @Author Bruce
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AuthResource {

    String name();

    String code();

    String parentCode() default "";

    MethodEnum method() default MethodEnum.GET;

    int order() default 1;

    String version() default AuthConstant.DEFAULT_VERSION;

}
