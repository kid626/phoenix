package com.bruce.phoenix.common.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/7/3 15:03
 * @Author Bruce
 */
@Slf4j
@UtilityClass
public class PhoenixDateUtil extends DateUtil {

    /**
     * 获取当前时间
     */
    public static DateTime nowDateTime() {
        return date();
    }

    /**
     * 时间转换 String - DateTime
     * 默认格式 yyyy-MM-dd HH:mm:ss
     */
    public static DateTime parse(String str) {
        return parse(str, DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 时间转换 String - DateTime
     * 傻瓜式 parse 会尝试多种常见格式
     */
    public static DateTime foolyParse(String str) {
        try {
            return parse(str, DatePattern.NORM_DATETIME_PATTERN);
        } catch (Exception e) {
            try {
                return parse(str, DatePattern.NORM_DATE_PATTERN);
            } catch (Exception e1) {
                try {
                    return parse(str, DatePattern.UTC_SIMPLE_MS_PATTERN);
                } catch (Exception e3) {
                    try {
                        return parse(str, DatePattern.UTC_WITH_XXX_OFFSET_PATTERN);
                    } catch (Exception e4) {
                        try {
                            return parse(str, DatePattern.UTC_WITH_XXX_OFFSET_PATTERN);
                        } catch (Exception e5) {
                            log.warn("You stupid asshole,parse it yourself!");
                            throw e5;
                        }
                    }
                }
            }
        }
    }

    /**
     * 时间转换 DateTime - String
     * 默认格式 yyyy-MM-dd HH:mm:ss
     */
    public static String format(DateTime dateTime) {
        return format(dateTime, DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 获取当前时间 yyyyMMdd
     */
    public static String yyyyMMdd() {
        return format(nowDateTime(), DatePattern.PURE_DATE_PATTERN);
    }

    /**
     * 获取当前时间 yyyyMMddHHmmss
     */
    public static String yyyyMMddHHmmss() {
        return format(nowDateTime(), DatePattern.PURE_DATETIME_PATTERN);
    }


}
