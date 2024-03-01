package com.bruce.phoenix.common.util;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/1 9:49
 * @Author Bruce
 */
@Slf4j
public class ThreadLocalUtil {

    private static final ThreadLocal<String> THREAD_LOCAL = ThreadUtil.createThreadLocal(false);

    public static String getRequestId() {
        log.info("[ThreadLocalUtil#getRequestId] requestId={}", THREAD_LOCAL.get());
        return THREAD_LOCAL.get();
    }

    public static void setRequestId(String requestId) {
        log.info("[ThreadLocalUtil#setRequestId] requestId={}", requestId);
        THREAD_LOCAL.set(requestId);
    }

    public static void remove() {
        log.info("[ThreadLocalUtil#remove]");
        THREAD_LOCAL.remove();
    }


}
