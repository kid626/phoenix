package com.bruce.phoenix.common.util;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.RandomUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/12/17 21:00
 * @Author Bruce
 */
public class StreamUtilTest {

    @Test
    public void order() {
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(RandomUtil.randomLong(1, 200));
        }
        Console.log("before:");
        for (Long l : list) {
            Console.log(l);
        }
        Map<String, Long> map = StreamUtil.order(list, (t) -> t + "", 10);
        Console.log("after:");
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            Console.log(entry.getKey() + ":" + entry.getValue());
        }
    }

}
