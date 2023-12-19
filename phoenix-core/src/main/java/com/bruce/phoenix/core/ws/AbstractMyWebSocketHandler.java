package com.bruce.phoenix.core.ws;

import com.bruce.phoenix.core.util.WsSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName demo
 * @Date 2021/10/13 14:32
 * @Author fzh
 */
@Slf4j
public abstract class AbstractMyWebSocketHandler extends TextWebSocketHandler {


    /**
     * socket 建立成功事件
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = getSessionId(session);
        log.info("连接建立,sessionId:{}", sessionId);
        WsSessionUtil.add(sessionId, session);
    }

    /**
     * 接收消息事件
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 获得客户端传来的消息
        String payload = message.getPayload();
        log.info("message:{}", payload);
        handleMessage(session, payload);
    }

    /**
     * socket 断开连接时
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String sessionId = getSessionId(session);
        log.info("连接断开,sessionId:{}", sessionId);
        // 用户退出，移除缓存
        WsSessionUtil.remove(sessionId);
    }

    private String getSessionId(WebSocketSession session) {
        return session.getId();
    }

    protected abstract void handleMessage(WebSocketSession session, String payload) throws Exception;

}
