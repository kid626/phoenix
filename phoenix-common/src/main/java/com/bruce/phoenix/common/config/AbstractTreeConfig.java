package com.bruce.phoenix.common.config;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.parser.NodeParser;
import com.bruce.phoenix.common.model.common.BaseTreeVO;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/4/10 09:09
 * @Author Bruce
 */
public interface AbstractTreeConfig<T> {

    /**
     * 节点与 tree 的转化
     */
    default NodeParser<BaseTreeVO<T>, String> node2Tree() {
        return ((node, tree) -> {
            tree.setId(node.getCode());
            tree.setParentId(node.getPCode());
            tree.setName(node.getName());
            tree.setWeight(getPriority(node));
            tree.putExtra("ext", node.getExt());
        });
    }

    /**
     * tree 与节点的转化
     */
    default BaseTreeVO<T> tree2Node(Tree<String> tree) {
        BaseTreeVO<T> vo = new BaseTreeVO<>();
        vo.setCode(tree.getId());
        vo.setPCode(tree.getParentId());
        vo.setName(tree.getName().toString());
        vo.setSort((Integer) tree.getWeight());
        vo.setExt((T) tree.get("ext"));
        return vo;
    }

    /**
     * 判断节点是否符合
     */
    default boolean isMatched(BaseTreeVO<T> node, String keyword) {
        return node != null;
    }

    default int getPriority(BaseTreeVO<T> node) {
        return node.getSort();
    }


}
