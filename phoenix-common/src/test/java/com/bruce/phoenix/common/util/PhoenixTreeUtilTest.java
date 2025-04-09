package com.bruce.phoenix.common.util;

import cn.hutool.core.lang.Console;
import com.bruce.phoenix.common.model.common.BaseTreeVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/4/9 09:11
 * @Author Bruce
 */
public class PhoenixTreeUtilTest {

    @Test
    public void build() {
        // 模拟节点数据
        List<BaseTreeVO<TreeVO>> nodeList = new ArrayList<>();

        BaseTreeVO<TreeVO> tree1 = new BaseTreeVO<>();
        tree1.setCode("1");
        tree1.setPCode("0");
        tree1.setName("水果");
        tree1.setExt(new TreeVO("1", "水果"));
        nodeList.add(tree1);

        BaseTreeVO<TreeVO> tree2 = new BaseTreeVO<>();
        tree2.setCode("2");
        tree2.setPCode("1");
        tree2.setName("苹果");
        tree2.setExt(new TreeVO("2", "苹果"));
        nodeList.add(tree2);

        BaseTreeVO<TreeVO> tree3 = new BaseTreeVO<>();
        tree3.setCode("3");
        tree3.setPCode("1");
        tree3.setName("香蕉");
        tree3.setExt(new TreeVO("3", "香蕉"));
        nodeList.add(tree3);

        BaseTreeVO<TreeVO> tree4 = new BaseTreeVO<>();
        tree4.setCode("4");
        tree4.setPCode("1");
        tree4.setName("红苹果");
        tree4.setExt(new TreeVO("4", "红苹果"));
        nodeList.add(tree4);

        List<BaseTreeVO<TreeVO>> list = PhoenixTreeUtil.build(nodeList, "0");
        Console.log(list);
    }

    @Test
    public void fuzzySearch() {
        // 模拟节点数据
        List<BaseTreeVO<TreeVO>> nodeList = new ArrayList<>();

        BaseTreeVO<TreeVO> tree1 = new BaseTreeVO<>();
        tree1.setCode("1");
        tree1.setPCode("0");
        tree1.setName("水果");
        tree1.setExt(new TreeVO("1", "水果"));
        nodeList.add(tree1);

        BaseTreeVO<TreeVO> tree2 = new BaseTreeVO<>();
        tree2.setCode("2");
        tree2.setPCode("1");
        tree2.setName("苹果");
        tree2.setExt(new TreeVO("2", "苹果"));
        nodeList.add(tree2);

        BaseTreeVO<TreeVO> tree3 = new BaseTreeVO<>();
        tree3.setCode("3");
        tree3.setPCode("1");
        tree3.setName("香蕉");
        tree3.setExt(new TreeVO("3", "香蕉"));
        nodeList.add(tree3);

        BaseTreeVO<TreeVO> tree4 = new BaseTreeVO<>();
        tree4.setCode("4");
        tree4.setPCode("1");
        tree4.setName("红苹果");
        tree4.setExt(new TreeVO("4", "红苹果"));
        nodeList.add(tree4);

        List<BaseTreeVO<TreeVO>> list = PhoenixTreeUtil.fuzzySearch(nodeList, "0", "果");
        Console.log(list);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TreeVO {
        private String id;
        private String name;
    }

}
