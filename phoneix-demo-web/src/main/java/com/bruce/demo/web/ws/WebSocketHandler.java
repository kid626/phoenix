package com.bruce.demo.web.ws;

import com.bruce.phoenix.core.ws.AbstractMyWebSocketHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName websocket
 * @Date 2021/2/4 21:53
 * @Author Bruce
 */
@Component
public class WebSocketHandler extends AbstractMyWebSocketHandler {


    @Override
    protected void handleMessage(WebSocketSession session, String payload) throws Exception {
        session.sendMessage(new TextMessage(payload));
    }

}
