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
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumValid {
	String message() default "";

	// 作用参考@Validated和@Valid的区别
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * 目标枚举类
	 */
	Class<?> target() default Class.class;

	/**
	 * 是否忽略空值
	 */
	boolean ignoreEmpty() default true;
}
