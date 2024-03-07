package com.bruce.phoenix.auth.util;

import javax.servlet.http.Cookie;
import java.time.Duration;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/6 19:10
 * @Author Bruce
 */
public class CookieUtil {

    public static Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge((int) Duration.ofDays(30).getSeconds());
        cookie.setPath("/");
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        return cookie;
    }
}
