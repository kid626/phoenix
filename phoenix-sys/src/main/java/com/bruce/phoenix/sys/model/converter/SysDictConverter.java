package com.bruce.phoenix.sys.model.converter;

import cn.hutool.core.lang.tree.Tree;
import com.bruce.phoenix.sys.model.form.SysDictForm;
import com.bruce.phoenix.sys.model.po.SysDict;
import com.bruce.phoenix.sys.model.vo.SysDictVO;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc
 * @ProjectName demo
 * @Date 2022/6/30 14:41
 * @Author fzh
 */
public class SysDictConverter extends BaseConverter<SysDictForm, SysDict, SysDictVO> {

    private static final String CODE = "code";
    private static final String P_CODE = "pCode";
    private static final String VALUE = "value";

    @Override
    protected void doConvert2Po(SysDictForm form, SysDict po) {

    }

    @Override
    protected void doConvert2Vo(SysDict po, SysDictVO vo) {

    }

    public static void convert2Tree(SysDict node, Tree<Long> treeNode) {
        treeNode.setId(node.getId());
        treeNode.setName(node.getDictName());
        treeNode.setParentId(node.getPId());
        treeNode.setWeight(node.getSortNo());
        treeNode.putExtra(CODE, node.getDictCode());
        treeNode.putExtra(P_CODE, node.getPCode());
        treeNode.putExtra(VALUE, node.getDictValue());
    }

    public static SysDictVO convert2VO(Tree<Long> tree) {
        SysDictVO vo = new SysDictVO();
        vo.setId(vo.getId());
        vo.setDictName(tree.getName() == null ? "" : tree.getName() + "");
        vo.setPId(tree.getParentId());
        vo.setSortNo(tree.getWeight() == null ? 0 : (Integer) tree.getWeight());
        vo.setDictCode(tree.get(CODE) == null ? "" : tree.get(CODE) + "");
        vo.setPCode(tree.get(P_CODE) == null ? "" : tree.get(P_CODE) + "");
        vo.setDictValue(tree.get(VALUE) == null ? "" : tree.get(VALUE) + "");
        return vo;
    }

}
