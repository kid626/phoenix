package com.bruce.phoenix.common.util;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/7/3 15:37
 * @Author Bruce
 */
public class PhoenixAssertUtilTest {

    @Test
    public void testTestAssertTrue() {
        PhoenixAssertUtil.assertTrue(1 == 1, "1=1");
        PhoenixAssertUtil.assertTrue(1 == 2, "1!=2");
    }

    @Test
    public void testTestAssertNotNull() {
        Object obj = new Object();
        PhoenixAssertUtil.assertNotNull(obj, "obj is null");
        obj = null;
        PhoenixAssertUtil.assertNotNull(obj, "obj is null");
    }

    @Test
    public void testAssertNotEmpty() {
        PhoenixAssertUtil.assertNotEmpty("1", "str is empty");
        PhoenixAssertUtil.assertNotEmpty("", "str is empty");
    }

    @Test
    public void testAssertNotBlank() {
        PhoenixAssertUtil.assertNotBlank("1", "str is blank");
        PhoenixAssertUtil.assertNotBlank("", "str is blank");
    }

    @Test
    public void testTestAssertNotEmpty() {
        List<String> list = new ArrayList<>();
        PhoenixAssertUtil.assertNotEmpty(list, "list is empty");
        list = null;
        PhoenixAssertUtil.assertNotEmpty(list, "list is empty");
    }
}
