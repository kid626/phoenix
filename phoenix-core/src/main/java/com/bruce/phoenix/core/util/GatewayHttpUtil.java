package com.bruce.phoenix.core.util;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.bruce.phoenix.common.exception.CommonException;
import com.bruce.phoenix.common.model.common.Result;
import com.bruce.phoenix.common.model.constants.CommonConstant;
import com.bruce.phoenix.common.util.BaseHttpUtil;
import com.bruce.phoenix.common.model.common.Err;
import com.bruce.phoenix.core.model.gateway.GatewaySignModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc 网关请求组件，参考shenyu网关的鉴权方式
 * @ProjectName phoenix
 * @Date 2024/2/28 18:17
 * @Author Bruce
 */
@Slf4j
public class GatewayHttpUtil {


    public final static String DEFAULT_VERSION = "1.0.0";


    /**
     * 发送json内容的请求
     *
     * @param model   GatewaySignModel
     * @param headers 请求头
     * @param params  body 内容，json 格式
     */
    public static String post(GatewaySignModel model, Map<String, String> headers, String params) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        model.setTimestamp(timestamp);
        Map<String, String> map = generateMap(model);
        // 进行签名
        String sign = generateSign(map, model.getAppSecret());
        if (headers == null) {
            headers = new HashMap<>(4);
        }
        headers.put(CommonConstant.TIMESTAMP, timestamp);
        headers.put(CommonConstant.APP_KEY, model.getAppKey());
        headers.put(CommonConstant.SIGN, sign);
        headers.put(CommonConstant.VERSION, StrUtil.isBlank(model.getVersion()) ? DEFAULT_VERSION : model.getVersion());
        String body = BaseHttpUtil.post(model.getHost() + model.getPath(), headers, params);
        return handleBody(body);
    }

    /**
     * 发送form内容的请求
     *
     * @param model   GatewaySignModel
     * @param headers 请求头
     * @param params  请求参数
     */
    public static String post(GatewaySignModel model, Map<String, String> headers, Map<String, String> params) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        model.setTimestamp(timestamp);
        Map<String, String> map = generateMap(model);
        // 进行签名
        String sign = generateSign(map, model.getAppSecret());
        if (headers == null) {
            headers = new HashMap<>(4);
        }
        headers.put(CommonConstant.TIMESTAMP, timestamp);
        headers.put(CommonConstant.APP_KEY, model.getAppKey());
        headers.put(CommonConstant.SIGN, sign);
        headers.put(CommonConstant.VERSION, StrUtil.isBlank(model.getVersion()) ? DEFAULT_VERSION : model.getVersion());
        String body = BaseHttpUtil.post(model.getHost() + model.getPath(), headers, params);
        return handleBody(body);
    }

    /**
     * 发送get请求
     *
     * @param model   GatewaySignModel
     * @param headers 请求头
     * @param params  请求参数
     */
    public static String get(GatewaySignModel model, Map<String, String> headers, Map<String, Object> params) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        model.setTimestamp(timestamp);
        Map<String, String> map = generateMap(model);
        // 进行签名
        String sign = generateSign(map, model.getAppSecret());
        if (headers == null) {
            headers = new HashMap<>(4);
        }
        headers.put(CommonConstant.TIMESTAMP, timestamp);
        headers.put(CommonConstant.APP_KEY, model.getAppKey());
        headers.put(CommonConstant.SIGN, sign);
        headers.put(CommonConstant.VERSION, StrUtil.isBlank(model.getVersion()) ? DEFAULT_VERSION : model.getVersion());
        String body = BaseHttpUtil.get(model.getHost() + model.getPath(), headers, params);
        return handleBody(body);
    }


    private static String handleBody(String body) {

        Result<Object> result = JSONUtil.toBean(body, new TypeReference<Result<Object>>() {
        }, true);
        if (!result.isSuccess()) {
            log.warn("[GatewayHttpUtil] 业务错误 code={} message={}", result.getCode(), result.getMsg());
            throw new CommonException(Err.GATEWAY_ERROR.getCode(), result.getMsg());
        }
        if (result.getData() instanceof String) {
            return String.valueOf(result.getData());
        }

        if (result.getData() instanceof Integer) {
            return String.valueOf(result.getData());
        }

        if (result.getData() instanceof Boolean) {
            return String.valueOf(result.getData());
        }
        String jsonStr = JSONUtil.toJsonStr(result.getData());
        if (JSONUtil.isTypeJSONObject(jsonStr)) {
            if (JSONUtil.parseObj(result.getData()).isEmpty()) {
                return "";
            }
        } else if (JSONUtil.isTypeJSONArray(jsonStr)) {
            if (JSONUtil.parseArray(result.getData()).isEmpty()) {
                return "";
            }
        }
        return JSONUtil.toJsonStr(result.getData());
    }

    private static Map<String, String> generateMap(GatewaySignModel model) {
        Map<String, String> map = new HashMap<>(4);
        map.put(CommonConstant.TIMESTAMP, model.getTimestamp());
        map.put(CommonConstant.PATH, model.getPath());
        map.put(CommonConstant.VERSION, StrUtil.isBlank(model.getVersion()) ? DEFAULT_VERSION : model.getVersion());
        return map;
    }


    /**
     * 生成签名
     *
     * @param map Map<String, String>
     * @return 签名值
     */
    public static String generateSign(Map<String, String> map, String appSecret) {
        // 根据字母顺序排序
        List<String> storedKeys = Arrays.stream(map.keySet().toArray(new String[]{})).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        final String sign = storedKeys.stream().filter(key -> !Objects.equals(key, CommonConstant.SIGN)).map(key -> String.join("", key, map.get(key))).collect(Collectors.joining()).trim().concat(appSecret);
        return DigestUtils.md5DigestAsHex(sign.getBytes()).toUpperCase();
    }

}
