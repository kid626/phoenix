package com.bruce.phoenix.core.ws;

import com.bruce.phoenix.core.util.WsSessionUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Collection;

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
    public void afterConnectionEstablished(@Nonnull WebSocketSession session) throws Exception {
        String sessionId = getSessionId(session);
        log.info("[AbstractMyWebSocketHandler#afterConnectionEstablished] 成功建立连接 sessionId={}", sessionId);
        WsSessionUtil.add(sessionId, session);
    }

    /**
     * 接收消息事件
     */
    @Override
    protected void handleTextMessage(@Nonnull WebSocketSession session, TextMessage message) throws Exception {
        // 获得客户端传来的消息
        String payload = message.getPayload();
        String sessionId = getSessionId(session);
        log.info("[AbstractMyWebSocketHandler#handleTextMessage] 接收消息事件 sessionId={},payload={}", sessionId, payload);
        handleMessage(sessionId, payload);
    }

    /**
     * socket 断开连接时
     */
    @SneakyThrows
    @Override
    public void afterConnectionClosed(@Nonnull WebSocketSession session, @Nonnull CloseStatus status) {
        String sessionId = getSessionId(session);
        log.info("[AbstractMyWebSocketHandler#afterConnectionClosed] 断开连接,sessionId={}", sessionId);
        // 用户退出，移除缓存 同事关闭 session
        WsSessionUtil.remove(sessionId);
        session.close();
    }


    /**
     * 发送消息给指定客户端
     *
     * @param sessionId socket 唯一主键
     * @param message   发送消息内容
     */
    public void sendMessage(String sessionId, String message) throws IOException {
        log.info("[AbstractMyWebSocketHandler#sendMessage] 发送消息 sessionId={},message={}", sessionId, message);
        WebSocketSession session = WsSessionUtil.get(sessionId);
        if (session != null) {
            session.sendMessage(new TextMessage(message));
        }
    }

    /**
     * 广播消息
     *
     * @param message 发送消息内容
     */
    public void broadcastMessage(String message) throws IOException {
        log.info("[AbstractMyWebSocketHandler#sendMessage] 广播消息 message={}", message);
        Collection<WebSocketSession> all = WsSessionUtil.values();
        for (WebSocketSession session : all) {
            session.sendMessage(new TextMessage(message));
        }
    }

    protected abstract String getSessionId(@Nonnull WebSocketSession session);

    protected abstract void handleMessage(String sessionId, String payload) throws Exception;

}
