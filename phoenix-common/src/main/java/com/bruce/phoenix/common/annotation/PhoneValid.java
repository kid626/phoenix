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
 * @Date 2021/10/9 11:04
 * @Author fzh
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
public @interface PhoneValid {

    String regex() default "^(?:(?:\\+|00)86)?1[3-9]\\d{9}$";

    String message() default "手机号校验失败";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
