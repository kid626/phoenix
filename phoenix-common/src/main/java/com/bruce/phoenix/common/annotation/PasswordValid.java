package com.bruce.phoenix.common.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName validate
 * @Date 2021/10/8 16:17
 * @Author fzh
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordValid {

    /**
     * 最短
     */
    int min() default 8;

    /**
     * 最长
     */
    int max() default 16;

    /**
     * 正则
     */
    String regex() default "(?=.*([a-zA-Z].*))(?=.*[0-9].*)[a-zA-Z0-9-*/+.~!@#$%^&*()]{8,}$";

    String message() default "密码校验失败";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
