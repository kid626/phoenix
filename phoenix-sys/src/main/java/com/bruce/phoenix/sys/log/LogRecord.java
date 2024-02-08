package com.bruce.phoenix.sys.log;

import com.bruce.phoenix.common.model.enums.BusinessTypeEnum;
import com.bruce.phoenix.common.model.enums.OperatorTypeEnum;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/2/7 19:12
 * @Author Bruce
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Order(value = 2)
public @interface LogRecord {

    /**
     * 模块
     */
    public String module() default "";

    /**
     * 功能
     */
    public BusinessTypeEnum businessType() default BusinessTypeEnum.OTHER;

    /**
     * 操作来源
     */
    public OperatorTypeEnum operatorType() default OperatorTypeEnum.OTHER;

    /**
     * 是否保存操作用户信息
     */
    public boolean isSaveUser() default true;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

    /**
     * 是否保存返回的参数
     */
    public boolean isSaveResponseData() default false;

}
