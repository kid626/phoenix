package com.bruce.phoenix.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.bruce.phoenix.common.config.AbstractTreeConfig;
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

    /**
     * 构建树
     *
     * @param all      所有节点
     * @param parentId 父id
     * @param <T>      节点额外信息
     * @return 树
     */
    public static <T> List<BaseTreeVO<T>> build(List<BaseTreeVO<T>> all, String parentId) {
        List<Tree<String>> list = TreeUtil.build(all, parentId, (node, tree) -> {
            tree.setId(node.getCode());
            tree.setParentId(node.getPCode());
            tree.setName(node.getName());
            tree.putExtra("ext", node.getExt());
        });
        return executeTree(list);
    }

    /**
     * 构建搜索树
     *
     * @param all      所有节点
     * @param parentId 父id
     * @param keyword  关键字
     * @param <T>      节点额外信息
     * @return 含关键字的节点，并以树结构返回
     */
    public static <T> List<BaseTreeVO<T>> fuzzySearch(List<BaseTreeVO<T>> all, String parentId, String keyword) {
        List<BaseTreeVO<T>> list = build(all, parentId);
        return fuzzySearch(list, keyword);
    }

    /**
     * 构建搜索树
     *
     * @param list    已经构建好的树
     * @param keyword 关键字
     * @param <T>     节点额外信息
     * @return 含关键字的节点，并以树结构返回
     */
    public static <T> List<BaseTreeVO<T>> fuzzySearch(List<BaseTreeVO<T>> list, String keyword) {
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

    /**
     * 构建树
     *
     * @param all        所有节点
     * @param parentId   父id
     * @param <T>        节点额外信息
     * @param treeConfig 树配置
     * @return 树
     */

    public static <T> List<BaseTreeVO<T>> build(List<BaseTreeVO<T>> all, String parentId, AbstractTreeConfig<T> treeConfig) {
        List<Tree<String>> list = TreeUtil.build(all, parentId, treeConfig.node2Tree());
        return executeTree(list, treeConfig);
    }

    /**
     * 构建搜索树
     *
     * @param all        所有节点
     * @param parentId   父id
     * @param keyword    关键字
     * @param <T>        节点额外信息
     * @param treeConfig 树配置
     * @return 含关键字的节点，并以树结构返回
     */
    public static <T> List<BaseTreeVO<T>> fuzzySearch(List<BaseTreeVO<T>> all, String parentId, String keyword, AbstractTreeConfig<T> treeConfig) {
        List<BaseTreeVO<T>> list = build(all, parentId, treeConfig);
        return fuzzySearch(list, keyword, treeConfig);
    }

    /**
     * 构建搜索树
     *
     * @param list       已经构建好的树
     * @param keyword    关键字
     * @param <T>        节点额外信息
     * @param treeConfig 树配置
     * @return 含关键字的节点，并以树结构返回
     */
    public static <T> List<BaseTreeVO<T>> fuzzySearch(List<BaseTreeVO<T>> list, String keyword, AbstractTreeConfig<T> treeConfig) {
        List<BaseTreeVO<T>> result = new ArrayList<>();
        if (CollUtil.isEmpty(list)) {
            return result;
        }
        for (BaseTreeVO<T> tree : list) {
            BaseTreeVO<T> matchedTree = findMatchedSubtree(tree, keyword);
            if (treeConfig.isMatched(matchedTree, keyword)) {
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
                vo.setName(StrUtil.isNotBlank(tree.getName()) ? tree.getName().toString() : "");
                //noinspection unchecked
                vo.setExt((T) tree.get("ext"));
                vo.setChild(executeTree(tree.getChildren()));
                vo.setHasChildren(CollUtil.isNotEmpty(tree.getChildren()));
                result.add(vo);
            }
        }
        return result;
    }


    private static <T> List<BaseTreeVO<T>> executeTree(List<Tree<String>> list, AbstractTreeConfig<T> treeConfig) {
        List<BaseTreeVO<T>> result = new ArrayList<>();
        if (CollUtil.isNotEmpty(list)) {
            for (Tree<String> tree : list) {
                BaseTreeVO<T> vo = treeConfig.tree2Node(tree);
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
