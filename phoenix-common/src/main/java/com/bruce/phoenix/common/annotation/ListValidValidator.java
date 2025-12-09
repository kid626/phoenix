package com.bruce.phoenix.common.annotation;

import cn.hutool.core.collection.CollUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/12/9 09:44
 * @Author Bruce
 */
public class ListValidValidator implements ConstraintValidator<ListValid, List<?>> {
    @Override
    public void initialize(ListValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext constraintValidatorContext) {
        if (CollUtil.isEmpty(value)) {
            return false;
        }
        return true;
    }
}
