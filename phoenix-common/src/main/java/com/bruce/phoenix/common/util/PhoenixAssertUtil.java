package com.bruce.phoenix.common.util;

import com.bruce.phoenix.common.exception.CommonException;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/7/3 15:26
 * @Author Bruce
 */
@UtilityClass
public class PhoenixAssertUtil {


    /**
     * 断言
     *
     * @param expression 表达式
     * @param message    如果表达式为 false，抛出异常
     */
    public static void assertTrue(boolean expression, String message) {
        if (!expression) {
            throw new CommonException(message);
        }
    }

    /**
     * 断言不为空
     *
     * @param o       对象
     * @param message 异常
     */
    public static void assertNotNull(Object o, String message) {
        assertTrue(o != null, message);
    }

    /**
     * 断言字符串不为空
     *
     * @param str     字符串
     * @param message 异常
     */
    public static void assertNotEmpty(String str, String message) {
        assertTrue(PhoenixStrUtil.isNotEmpty(str), message);
    }

    /**
     * 断言字符串不为空
     *
     * @param str     字符串
     * @param message 异常
     */
    public static void assertNotBlank(String str, String message) {
        assertTrue(PhoenixStrUtil.isNotBlank(str), message);
    }

    /**
     * 断言集合不为空
     *
     * @param list    集合
     * @param message 异常
     */
    public static <T> void assertNotEmpty(List<T> list, String message) {
        assertTrue(PhoenixCollUtil.isNotEmpty(list), message);
    }

}
