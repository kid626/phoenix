package com.bruce.demo.web.ws;

import com.bruce.phoenix.common.util.UrlUtil;
import com.bruce.phoenix.core.util.WsSessionUtil;
import com.bruce.phoenix.core.ws.AbstractMyWebSocketHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Nonnull;
import java.net.URI;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName websocket
 * @Date 2021/2/4 21:53
 * @Author Bruce
 */
@Component
public class WebSocketHandler extends AbstractMyWebSocketHandler {

    private static final String USER_ID = "userId";

    @Override
    protected String getSessionId(@Nonnull WebSocketSession session) {
        URI uri = session.getUri();
        return UrlUtil.getParameter(uri.getQuery(), USER_ID);
    }

    @Override
    protected void handleMessage(String sessionId, String payload) throws Exception {
        WebSocketSession session = WsSessionUtil.get(sessionId);
        session.sendMessage(new TextMessage(payload));
    }

}
