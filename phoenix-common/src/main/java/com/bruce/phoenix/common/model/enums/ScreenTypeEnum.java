package com.bruce.phoenix.common.model.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc 大屏类型枚举
 * @ProjectName phoenix
 * @Date 2024/9/13 14:20
 * @Author Bruce
 */
public enum ScreenTypeEnum implements BaseServiceEnum {

    /**
     *
     */
    BAR("bar", "柱状图"),
    LINE("line", "折线图"),
    PIE("pie", "饼图"),
    ;

    ScreenTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;


    public static final Map<String, String> LOOKUP = new HashMap<>();

    static {
        for (ScreenTypeEnum s : EnumSet.allOf(ScreenTypeEnum.class)) {
            LOOKUP.put(s.getCode(), s.getName());
        }
    }

    @Override
    public String toString() {
        return this.getCode();
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
