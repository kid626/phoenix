package com.bruce.phoenix.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.bruce.phoenix.common.model.common.BaseTreeVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/4/9 09:03
 * @Author Bruce
 */
public class PhoenixTreeUtil {

    public static <T> List<BaseTreeVO<T>> build(List<BaseTreeVO<T>> all, String parentId) {
        List<Tree<String>> list = TreeUtil.build(all, parentId, (node, tree) -> {
            tree.setId(node.getCode());
            tree.setParentId(node.getPCode());
            tree.setName(node.getName());
            tree.putExtra("ext", node.getExt());
        });
        return executeTree(list);
    }


    public static <T> List<BaseTreeVO<T>> fuzzySearch(List<BaseTreeVO<T>> all, String parentId, String keyword) {
        List<BaseTreeVO<T>> list = build(all, parentId);
        List<BaseTreeVO<T>> result = new ArrayList<>();
        if (CollUtil.isEmpty(list)) {
            return result;
        }
        for (BaseTreeVO<T> tree : list) {
            BaseTreeVO<T> matchedTree = findMatchedSubtree(tree, keyword);
            if (matchedTree != null) {
                result.add(matchedTree);
            }
        }
        return result;
    }


    private static <T> List<BaseTreeVO<T>> executeTree(List<Tree<String>> list) {
        List<BaseTreeVO<T>> result = new ArrayList<>();
        if (CollUtil.isNotEmpty(list)) {
            for (Tree<String> tree : list) {
                BaseTreeVO<T> vo = new BaseTreeVO<>();
                vo.setCode(tree.getId());
                vo.setPCode(tree.getParentId());
                vo.setName(tree.getName().toString());
                vo.setExt((T) tree.get("ext"));
                vo.setChild(executeTree(tree.getChildren()));
                vo.setHasChildren(CollUtil.isNotEmpty(tree.getChildren()));
                result.add(vo);
            }
        }
        return result;
    }

    private static <T> BaseTreeVO<T> findMatchedSubtree(BaseTreeVO<T> tree, String keyword) {
        boolean isCurrentNodeMatched = StrUtil.contains(tree.getName(), keyword);
        BaseTreeVO<T> result = null;
        if (isCurrentNodeMatched) {
            result = cloneTree(tree);
        }
        List<BaseTreeVO<T>> children = tree.getChild();
        List<BaseTreeVO<T>> matchedChildren = new ArrayList<>();
        for (BaseTreeVO<T> child : children) {
            BaseTreeVO<T> matchedChild = findMatchedSubtree(child, keyword);
            if (matchedChild != null) {
                if (result == null) {
                    result = cloneTree(tree);
                }
                matchedChildren.add(matchedChild);
            }
        }
        if (result != null) {
            result.setChild(matchedChildren);
        }
        return result;
    }

    private static <T> BaseTreeVO<T> cloneTree(BaseTreeVO<T> tree) {
        BaseTreeVO<T> cloned = new BaseTreeVO<>();
        cloned.setCode(tree.getCode());
        cloned.setName(tree.getName());
        cloned.setPCode(tree.getPCode());
        cloned.setExt(tree.getExt());
        cloned.setHasChildren(tree.isHasChildren());
        cloned.setChild(tree.getChild());
        return cloned;
    }

}
