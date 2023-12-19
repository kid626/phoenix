package com.bruce.demo.web.ws;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bruce.phoenix.common.util.UrlUtil;
import com.bruce.phoenix.core.ws.AbstractHandshakeInterceptor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;

import java.net.URI;
import java.util.Map;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName websocket
 * @Date 2021/2/4 21:53
 * @Author Bruce
 */
@Component
public class WebSocketInterceptor extends AbstractHandshakeInterceptor {


    private static final String TOKEN = "token";

    private String getToken(ServerHttpRequest serverHttpRequest) {
        URI uri = serverHttpRequest.getURI();
        return UrlUtil.getParameter(uri.getQuery(), TOKEN);
    }

    @Override
    protected boolean checkToken(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) {
        ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
        //获取参数
        String token = getToken(serverHttpRequest);
        if (StringUtils.isBlank(token)) {
            return false;
        }
        // TODO 校验 token 有效性
        return true;
    }
}
