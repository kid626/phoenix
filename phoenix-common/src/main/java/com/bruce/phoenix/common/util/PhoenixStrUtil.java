package com.bruce.phoenix.common.util;

import cn.hutool.core.util.StrUtil;
import com.bruce.phoenix.common.model.constants.CommonConstant;
import lombok.experimental.UtilityClass;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/7/3 14:14
 * @Author Bruce
 */
@UtilityClass
public class PhoenixStrUtil extends StrUtil {

    /**
     * 获取字符串，为空则返回默认值
     *
     * @param str        字符串
     * @param defaultStr 默认字符串  不能为 null，否则没有意义！
     */
    public static String getOrDefault(String str, @NotNull String defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    /**
     * 字符串连接，默认用 , 分割
     *
     * @param list 字符串集合
     */
    public static String join(List<String> list) {
        return join(CommonConstant.COMMA, list);
    }

    /**
     * 字符串分割，默认用 , 分割
     *
     * @param str 原字符串
     */
    public static List<String> split(String str) {
        return split(str, CommonConstant.COMMA);
    }

}
