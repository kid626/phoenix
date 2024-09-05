package com.bruce.demo.web.model.enums;

import com.bruce.phoenix.common.model.enums.BaseServiceEnum;
import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/5 15:21
 * @Author Bruce
 */
public enum TestEnum implements BaseServiceEnum {

    /**
     *
     */
    CODE1("code1", "name1"),
    CODE2("code2", "name2");

    TestEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Getter
    private String code;
    @Getter
    private String name;


    public static final Map<String, String> LOOKUP = new HashMap<>();

    static {
        for (TestEnum s : EnumSet.allOf(TestEnum.class)) {
            LOOKUP.put(s.getCode(), s.getName());
        }
    }

    @Override
    public String toString() {
        return this.getCode();
    }
}
