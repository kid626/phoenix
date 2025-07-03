package com.bruce.phoenix.common.util;

import cn.hutool.core.lang.Console;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/7/3 14:51
 * @Author Bruce
 */
public class PhoenixCollUtilTest {

    @Test
    public void getOrDefault() {
        // List<String> list = Arrays.asList("123", "433", "tes");
        List<String> list = new ArrayList<>();
        list = PhoenixCollUtil.getOrDefault(list, Arrays.asList("1"));
        Console.log(list);
    }

}
