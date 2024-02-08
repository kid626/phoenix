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
public enum BusinessTypeEnum {


    /**
     * 操作功能类型
     */
    OTHER("other", "其它"),
    QUERY("query", "查询"),
    INSERT("insert", "新增"),
    UPDATE("update", "修改"),
    DELETE("delete", "删除"),
    EXPORT("export", "导出"),
    IMPORT("import", "导入");

    BusinessTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Getter
    private String code;
    @Getter
    private String name;


    public static final Map<String, String> LOOKUP = new HashMap<>();

    static {
        for (BusinessTypeEnum s : EnumSet.allOf(BusinessTypeEnum.class)) {
            LOOKUP.put(s.getCode(), s.getName());
        }
    }

    @Override
    public String toString() {
        return this.getCode();
    }
}
