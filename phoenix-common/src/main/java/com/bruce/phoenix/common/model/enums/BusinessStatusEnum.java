package com.bruce.phoenix.common.model.enums;

import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc
 * @ProjectName demo
 * @Date 2022/6/20 11:03
 * @Author fzh
 */
public enum BusinessStatusEnum {

    /**
     * 成功、失败
     */
    SUCCESS("success", "成功"),
    FAIL("fail", "失败");

    BusinessStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Getter
    private String code;
    @Getter
    private String name;


    public static final Map<String, String> LOOKUP = new HashMap<>();

    static {
        for (BusinessStatusEnum s : EnumSet.allOf(BusinessStatusEnum.class)) {
            LOOKUP.put(s.getCode(), s.getName());
        }
    }

    @Override
    public String toString() {
        return this.getCode();
    }

}
