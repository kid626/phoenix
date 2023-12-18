package com.bruce.phoenix.common.annotation;

import cn.hutool.core.util.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName validate
 * @Date 2021/10/9 11:04
 * @Author fzh
 */
public class PhoneValidator implements ConstraintValidator<PhoneValid, String> {

    private PhoneValid annotation;

    @Override
    public void initialize(PhoneValid annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 如果 value 为空则不进行格式验证，为空验证可以使用 @NotBlank @NotNull @NotEmpty 等注解来进行控制，职责分离
        if (StrUtil.isEmpty(value)) {
            return true;
        }
        Pattern pattern = Pattern.compile(annotation.regex());
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
