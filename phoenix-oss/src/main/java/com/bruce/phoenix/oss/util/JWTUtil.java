package com.bruce.phoenix.oss.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.bruce.phoenix.common.exception.CommonException;
import com.bruce.phoenix.core.model.oss.OssFileInfoModel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/29 16:23
 * @Author Bruce
 */
@Slf4j
public class JWTUtil {
    /**
     * 链接最长有效时间 单位：小时
     */
    public static final int MAX_VALID_MINUTES = 5;
    /**
     * 保存用户信息
     */
    public static ThreadLocal<OssFileInfoModel> TOKEN_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 密钥
     */
    public static final String SECRET = "sdjhakd#hajdklsl;o653632l";

    /**
     * 生成Token
     *
     * @param model OssFileInfoModel
     */
    public static String createToken(OssFileInfoModel model) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // 不参与签名
        model.setFileBase64("");

        // 生成Token
        String token;
        long expire = System.currentTimeMillis() + JWTUtil.MAX_VALID_MINUTES * 60 * 1000;
        token = JWT.create().addHeaders(map).addPayloads(BeanUtil.beanToMap(model)).setExpiresAt(new Date(expire)).sign(JWTSignerUtil.hs256(SECRET.getBytes(StandardCharsets.UTF_8)));
        return token;
    }

    /**
     * 解析Token
     *
     * @param token 生成的 jwt token
     */
    public static OssFileInfoModel verifyToken(String token) {
        JWT jwt = JWT.of(token);
        if (jwt.verify(JWTSignerUtil.hs256(SECRET.getBytes(StandardCharsets.UTF_8)))) {
            JSONObject payloads = jwt.getPayloads();
            return JSONUtil.toBean(payloads, OssFileInfoModel.class, true);
        }
        return null;
    }

    public static OssFileInfoModel getToken() {
        OssFileInfoModel model = TOKEN_THREAD_LOCAL.get();
        if (model == null) {
            throw new CommonException("token已过期");
        }
        return model;
    }

    public static void setToken(OssFileInfoModel token) {
        TOKEN_THREAD_LOCAL.set(token);
    }

    public static void removeToken() {
        TOKEN_THREAD_LOCAL.remove();
    }

}
