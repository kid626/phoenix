package com.bruce.phoenix.common.annotation;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/12/9 09:44
 * @Author Bruce
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ListValidValidator.class)
public @interface ListValid {
}
