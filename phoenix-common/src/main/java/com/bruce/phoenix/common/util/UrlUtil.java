package com.bruce.phoenix.common.util;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName websocket
 * @Date 2021/2/4 21:53
 * @Author Bruce
 */
@UtilityClass
public class UrlUtil {

    public static String getParameter(String url, String key) {
        return getParameter(url).get(key);
    }

    public static Map<String, String> getParameter(String url) {
        Map<String, String> map = new HashMap<>(16);
        String[] keyValues = url.split("&");
        for (String keyValue : keyValues) {
            String key = keyValue.substring(0, keyValue.indexOf("="));
            String value = keyValue.substring(keyValue.indexOf("=") + 1);
            map.put(key, value);
        }
        return map;
    }

}
