package com.bruce.phoenix.core.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/8/4 19:51
 * @Author Bruce
 */
@Component
public class ContentCacheFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if ("GET".equals(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }
        // 包装原始请求为 ContentCachingRequestWrapper
        // 仅缓存 POST/PUT 等有请求体的方法
        // 仅缓存 <=1MB 的请求体（1024×1024=1048576 字节）
        ContentCachingRequestWrapper cachedRequest = new ContentCachingRequestWrapper(request, 1048576);
        // 继续传递包装后的请求
        chain.doFilter(cachedRequest, response);
    }
}
