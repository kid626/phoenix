package com.bruce.phoenix.sys.model.converter;

import com.bruce.phoenix.sys.model.po.SysDict;
import com.bruce.phoenix.sys.model.tree.dict.SysDictForm;
import com.bruce.phoenix.sys.model.tree.dict.SysDictTree;
import com.bruce.phoenix.sys.model.tree.dict.SysDictVO;
import org.springframework.beans.BeanUtils;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc
 * @ProjectName demo
 * @Date 2022/6/30 14:41
 * @Author fzh
 */
public class SysDictConverter extends BaseConverter<SysDictForm, SysDictTree, SysDictVO> {


    public static SysDictTree convert2Tree(SysDict po) {
        SysDictTree tree = new SysDictTree();
        tree.setCode(po.getDictCode());
        tree.setName(po.getDictName());
        tree.setPId(po.getPId());
        tree.setId(po.getId());
        BeanUtils.copyProperties(po, tree);
        return tree;
    }

    public static SysDict convert2Po(SysDictTree tree) {
        SysDict po = new SysDict();
        po.setId(tree.getId());
        po.setPId(tree.getPId());
        po.setDictCode(tree.getCode());
        po.setDictName(tree.getName());
        BeanUtils.copyProperties(tree, po);
        return po;
    }

    @Override
    protected void doConvert2Po(SysDictForm form, SysDictTree po) {

    }

    @Override
    protected void doConvert2Vo(SysDictTree po, SysDictVO vo) {

    }

}
