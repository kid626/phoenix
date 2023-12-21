package com.bruce.phoenix.sys.service.impl;

import com.bruce.phoenix.common.tree.TreeNodeConvertService;
import com.bruce.phoenix.sys.model.tree.dict.SysDictForm;
import com.bruce.phoenix.sys.model.tree.dict.SysDictTree;
import com.bruce.phoenix.sys.model.tree.dict.SysDictVO;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2023/12/20 13:43
 * @Author Bruce
 */
public class SysDictConvertServiceImpl implements TreeNodeConvertService<SysDictTree, SysDictForm, SysDictVO> {


    @Override
    public SysDictVO convert2Vo(SysDictTree tree) {
        SysDictVO vo = new SysDictVO();
        vo.setCode(tree.getCode());
        vo.setId(tree.getId());
        vo.setPId(tree.getPId());
        vo.setName(tree.getName());
        // 额外的字段
        vo.setDictValue(tree.getDictValue());
        vo.setIsEnable(tree.getIsEnable());
        vo.setSortNo(tree.getSortNo());
        return vo;
    }

    @Override
    public SysDictTree convert2Po(SysDictForm form) {
        SysDictTree t = new SysDictTree();
        t.setId(form.getId());
        t.setPId(form.getPId());
        t.setName(form.getName());
        // 额外的字段
        t.setDictValue(form.getDictValue());
        t.setIsEnable(form.getIsEnable());
        t.setSortNo(form.getSortNo());
        return t;
    }
}
