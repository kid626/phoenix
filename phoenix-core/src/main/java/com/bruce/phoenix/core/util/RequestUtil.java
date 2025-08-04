package com.bruce.phoenix.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/8/4 19:40
 * @Author Bruce
 */
public class RequestUtil {

    /**
     * 从请求中获取缓存的请求体字符串
     */
    public static String getCachedBody(HttpServletRequest request) {
        // 判断请求是否被 ContentCachingRequestWrapper 包装
        if (request instanceof ContentCachingRequestWrapper) {
            ContentCachingRequestWrapper cachedRequest = (ContentCachingRequestWrapper) request;
            // 获取缓存的字节数组并转换为字符串（指定编码）
            byte[] content = cachedRequest.getContentAsByteArray();
            if (content.length > 0) {
                return new String(content, StandardCharsets.UTF_8);
            }
        }
        return "";
    }

    /**
     * 从 HttpServletRequest（可能被 ContentCachingRequestWrapper 包装过）中提取 JSON 中的 key，
     * 若没有该字段或内容不存在，返回 ""。
     */
    public static String getBodyParam(HttpServletRequest request, String key) {
        // 支持 GET 查询/表单参数
        String param = request.getParameter(key);
        if (param != null && !param.trim().isEmpty()) {
            return param.trim();
        }
        String body = getCachedBody(request);
        JSONObject jsonObject = JSON.parseObject(body);
        // 支持嵌套字段（如 "user.name"）
        return jsonObject.getString(key);
    }


}
