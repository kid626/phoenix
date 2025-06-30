package com.bruce.phoenix.sys.utl;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.RandomUtil;
import com.bruce.phoenix.common.model.constants.CommonConstant;
import com.bruce.phoenix.core.util.GatewayHttpUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/6/30 15:05
 * @Author Bruce
 */
public class GatewayHttpUtilTest {

    @Test
    public void random() {
        Console.log(RandomUtil.randomString(16).toUpperCase());
        Console.log(RandomUtil.randomString(32).toUpperCase());
    }

    @Test
    public void sign() {
        Map<String, String> map = new HashMap<>(4);
        map.put(CommonConstant.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        map.put(CommonConstant.PATH, "/demo/openapi");
        map.put(CommonConstant.VERSION, GatewayHttpUtil.DEFAULT_VERSION);
        map.put(CommonConstant.ACCESS_KEY, "8R1W7L0VAALIOU6Q");
        Console.log(map);
        String sign = GatewayHttpUtil.generateSign(map, "C4ORBMKPDP4TEPO2A86MK8A9PY9KNCD2");
        Console.log(sign);

    }


}
