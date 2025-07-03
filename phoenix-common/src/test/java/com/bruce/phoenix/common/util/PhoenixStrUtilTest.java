package com.bruce.phoenix.common.util;

import cn.hutool.core.lang.Console;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/7/3 14:53
 * @Author Bruce
 */
public class PhoenixStrUtilTest {

    @Test
    public void getOrDefault() {
        // String str = "hello world";
        String str = "";
        Console.log(PhoenixStrUtil.getOrDefault(str, "hello world!"));
    }

    @Test
    public void join() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        Console.log(PhoenixStrUtil.join(list));
    }

    @Test
    public void split() {
        String str = "1,2,3,4,5";
        Console.log(PhoenixStrUtil.split(str));
    }

}
