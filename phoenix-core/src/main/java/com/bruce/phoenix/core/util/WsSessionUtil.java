package com.bruce.phoenix.core.util;

import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc websocket session 管理
 * @ProjectName websocket
 * @Date 2021/2/4 21:53
 * @Author Bruce
 */
public class WsSessionUtil {

    /**
     * 保存连接 session 的地方
     */
    private static ConcurrentHashMap<String, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();

    /**
     * 添加 session
     *
     * @param key 自定义 key
     */
    public static void add(String key, WebSocketSession session) {
        // 添加 session
        SESSION_POOL.put(key, session);
    }

    /**
     * 删除 session,会返回删除的 session
     *
     * @param key 自定义 key
     */
    public static void remove(String key) {
        // 删除 session
        SESSION_POOL.remove(key);
    }


    /**
     * 获得 session
     *
     * @param key 自定义 key
     * @return WebSocketSession
     */
    public static WebSocketSession get(String key) {
        // 获得 session
        return SESSION_POOL.get(key);
    }

    public static Collection<WebSocketSession> values() {
        return SESSION_POOL.values();
    }

    public static Collection<String> keys() {
        return SESSION_POOL.keySet();
    }

}
