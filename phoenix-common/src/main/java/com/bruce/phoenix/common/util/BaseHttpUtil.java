package com.bruce.phoenix.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.bruce.phoenix.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/2/29 9:17
 * @Author Bruce
 */
@Slf4j
public class BaseHttpUtil {


    /**
     * 发送json内容的请求
     *
     * @param url     请求url
     * @param headers 请求头
     * @param params  body 内容，json 格式
     */
    public static String post(String url, Map<String, String> headers, String params) {
        log.info("[BaseHttpUtil#postJSON] url={},headers={},params={}", url, JSONUtil.toJsonStr(headers), params);
        if (headers == null) {
            headers = new HashMap<>(4);
        }
        HttpRequest httpRequest = HttpUtil.createPost(url);
        httpRequest.headerMap(headers, true);
        httpRequest.body(params);
        return execute(httpRequest);
    }

    /**
     * 发送form内容的请求
     *
     * @param url     请求url
     * @param headers 请求头
     * @param params  请求参数
     */
    public static String post(String url, Map<String, String> headers, Map<String, String> params) {
        log.info("[BaseHttpUtil#post] url={},headers={},params={}", url, JSONUtil.toJsonStr(headers), JSONUtil.toJsonStr(params));
        if (headers == null) {
            headers = new HashMap<>(4);
        }
        HttpRequest httpRequest = HttpUtil.createPost(url);
        httpRequest.headerMap(headers, true);
        StringBuilder sb = new StringBuilder();
        if (CollUtil.isNotEmpty(params)) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }
        httpRequest.body(sb.substring(0, sb.length() - 1));
        return execute(httpRequest);
    }

    /**
     * 发送get请求
     *
     * @param url     请求url
     * @param headers 请求头
     * @param params  请求参数
     */
    public static String get(String url, Map<String, String> headers, Map<String, Object> params) {
        log.info("[BaseHttpUtil#get] url={},headers={},params={}", url, JSONUtil.toJsonStr(headers), JSONUtil.toJsonStr(params));
        if (headers == null) {
            headers = new HashMap<>(4);
        }
        HttpRequest httpRequest = HttpUtil.createGet(url);
        httpRequest.headerMap(headers, true);
        httpRequest.form(params);
        return execute(httpRequest);
    }


    private static String execute(HttpRequest httpRequest) {
        try (HttpResponse response = httpRequest.execute()) {
            if (response == null) {
                // 一般不会走到这步
                log.warn("[GatewayHttpUtil] 请求异常!");
                throw new CommonException("请求异常!");
            }
            String body =response.body();
            if (response.isOk()) {
                return body;
            } else {
                log.warn("[BaseHttpUtil] 请求失败{}!", body);
                throw new CommonException(response.getStatus(), body);
            }
        } catch (CommonException e) {
            log.warn("[BaseHttpUtil] 请求业务错误 code={} msg={}", e.getCode(), e.getMessage());
            throw e;
        } catch (Exception e) {
            throw new CommonException("系统错误,请联系管理员!");
        }
    }

}
