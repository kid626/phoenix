package com.bruce.phoenix.common.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName security
 * @Date 2021/12/27 14:29
 * @Author fzh
 */
@Data
@AllArgsConstructor
public class Err {

    private int code;
    private String message;

    /**
     * 公共异常
     */
    public static Err SYS_ERROR = new Err(500, "系统异常");
    public static Err GATEWAY_ERROR = new Err(501, "网关异常");
    public static Err CUSTOM_ERROR = new Err(502, "自定义错误");
    public static Err PARAM_ERROR = new Err(400, "参数校验异常");
    public static Err NO_AUTH = new Err(401, "无权限访问");
    public static Err NO_LOGIN = new Err(430, "未登录");
}
