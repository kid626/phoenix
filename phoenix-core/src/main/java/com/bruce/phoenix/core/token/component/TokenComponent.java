package com.bruce.phoenix.core.token.component;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.thread.NamedThreadFactory;
import com.bruce.phoenix.core.token.model.BaseTokenModel;
import com.bruce.phoenix.core.token.service.DefaultTokenService;
import com.bruce.phoenix.core.token.service.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc 基于内存的token 管理组件
 * @ProjectName phoenix
 * @Date 2024/9/13 15:02
 * @Author Bruce
 */
@Slf4j
// @Component
public class TokenComponent {

    /**
     * token刷新间隔 60s
     */
    private static final long FRESH_TOKEN_INTERVAL = 60 * 1000;
    /**
     * token 过期时间 120s
     */
    private static final long TOKEN_EXPIRE_INTERVAL = 120 * 1000;
    // token管理map
    private Map<String, BaseTokenModel> TOKEN_MAP = new ConcurrentHashMap<>();
    // token 实现类管理map
    private Map<String, ITokenService> TOKEN_SERVICE_MAP = new ConcurrentHashMap<>();
    // 线程刷新token和保活
    private final ScheduledExecutorService REFRESH_TOKEN_SCHEDULED = newSingleThreadScheduledExecutor(new NamedThreadFactory("refresh-token", true));

    @PostConstruct
    public void init() {
        REFRESH_TOKEN_SCHEDULED.scheduleWithFixedDelay(() -> {
            try {
                // 刷新
                refresh();
            } catch (Exception e) {
                log.warn("Unexpected error occur at token refresh, cause: " + e.getMessage(), e);
            }
        }, FRESH_TOKEN_INTERVAL, FRESH_TOKEN_INTERVAL, TimeUnit.MILLISECONDS);
    }

    @Autowired
    private TokenComponent(List<ITokenService> list) {
        list.forEach(service -> {
            TOKEN_SERVICE_MAP.put(service.getType(), service);
        });
    }

    /**
     * 动态添加
     */
    public void addTokenService(ITokenService tokenService) {
        TOKEN_SERVICE_MAP.put(tokenService.getType(), tokenService);
    }

    /**
     * 动态删除
     */
    public void removeTokenService(String type) {
        TOKEN_SERVICE_MAP.remove(type);
    }

    public BaseTokenModel getTokenCache(String key) {
        BaseTokenModel baseTokenModel = TOKEN_MAP.get(key);
        if (baseTokenModel != null) {
            return baseTokenModel;
        }
        BaseTokenModel token = getToken(key);
        TOKEN_MAP.put(key, token);
        return token;
    }

    private void refresh() {
        log.info("[TokenComponent#refresh]刷新缓存，检查是否过期");
        if (CollUtil.isNotEmpty(TOKEN_MAP)) {
            for (Map.Entry<String, BaseTokenModel> entry : TOKEN_MAP.entrySet()) {
                BaseTokenModel token = entry.getValue();
                Long currentTime = System.currentTimeMillis();
                // 如果时间还剩120s，则刷新token
                if (token.getExpireTime() - currentTime <= TOKEN_EXPIRE_INTERVAL) {
                    log.info("[TokenComponent#refresh]token 过期，刷新token");
                    token = getToken(entry.getKey());
                    if (token != null) {
                        TOKEN_MAP.put(entry.getKey(), token);
                    }
                }
            }
        }
    }

    private BaseTokenModel getToken(String key) {
        return TOKEN_SERVICE_MAP.getOrDefault(key, new DefaultTokenService()).getToken();
    }

}
