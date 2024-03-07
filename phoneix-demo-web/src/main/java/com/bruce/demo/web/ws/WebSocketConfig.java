package com.bruce.demo.web.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName websocket
 * @Date 2021/2/4 21:53
 * @Author Bruce
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Resource
    private WebSocketHandler webSocketHandler;
    @Resource
    private WebSocketInterceptor webSocketInterceptor;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 如果引入了 auth ，需要将该 url 排除掉，因为 ws 本质还是走的 http 请求
        registry.addHandler(webSocketHandler, "/ws/demo")
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins("*");
    }
}
