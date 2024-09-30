package com.bruce.phoenix.oss.model.enums;

import com.bruce.phoenix.common.model.enums.BaseServiceEnum;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/29 14:24
 * @Author Bruce
 */
public enum FileTypeEnum implements BaseServiceEnum {

    /**
     * 文件类型枚举
     */
    ALL("all", "全部"),
    IMAGE("image", "图片"),
    VIDEO("video", "视频"),
    MUSIC("music", "音频"),
    DOC("doc", "文档"),
    OTHER("other", "其他");

    FileTypeEnum(String code, String name) {
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

    public static String getByCode(String code) {
        return LOOKUP.getOrDefault(code, "");
    }

    public static final Map<String, String> LOOKUP = new HashMap<String, String>();

    static {
        for (FileTypeEnum s : EnumSet.allOf(FileTypeEnum.class)) {
            LOOKUP.put(s.getCode(), s.getName());
        }
    }

    @Override
    public String toString() {
        return this.getCode();
    }

}
