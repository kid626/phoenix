package com.bruce.phoenix.common.util;

import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName security
 * @Date 2021/12/26 11:40
 * @Author fzh
 */
@UtilityClass
public class TraceUtil {

    private static ThreadLocal<String> TRACE = new ThreadLocal<>();

    public static void set(String traceId) {
        TRACE.set(traceId);
    }

    public static String get() {
        String traceId = TRACE.get();
        if (StrUtil.isBlank(traceId)) {
            traceId = UUID.randomUUID().toString().replaceAll("-", "");
            TRACE.set(traceId);
        }

        return traceId;
    }

    public static void clear() {
        TRACE.remove();
    }

}
