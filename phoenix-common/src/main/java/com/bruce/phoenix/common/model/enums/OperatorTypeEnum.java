package com.bruce.phoenix.common.model.enums;

import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc
 * @ProjectName demo
 * @Date 2022/6/20 10:27
 * @Author fzh
 */
public enum OperatorTypeEnum {

    /**
     * 操作用户类型
     */
    OTHER("other", "其它"),
    MANAGE("manage", "后台用户"),
    MOBILE("mobile", "手机端用户");

    OperatorTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Getter
    private String code;
    @Getter
    private String name;


    public static final Map<String, String> LOOKUP = new HashMap<>();

    static {
        for (OperatorTypeEnum s : EnumSet.allOf(OperatorTypeEnum.class)) {
            LOOKUP.put(s.getCode(), s.getName());
        }
    }

    @Override
    public String toString() {
        return this.getCode();
    }


}
