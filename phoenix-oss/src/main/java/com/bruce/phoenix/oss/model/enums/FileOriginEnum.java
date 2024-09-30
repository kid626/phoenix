package com.bruce.phoenix.oss.model.enums;

import com.bruce.phoenix.common.model.enums.BaseServiceEnum;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/30 15:36
 * @Author Bruce
 */
public enum FileOriginEnum implements BaseServiceEnum {

    /**
     *
     */
    CLIENT("client", "前端直接调用"),
    GATEWAY("gateway", "服务网关调用");

    FileOriginEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }


    public static final Map<String, String> LOOKUP = new HashMap<>();

    static {
        for (FileOriginEnum s : EnumSet.allOf(FileOriginEnum.class)) {
            LOOKUP.put(s.getCode(), s.getName());
        }
    }

    @Override
    public String toString() {
        return this.getCode();
    }
}
