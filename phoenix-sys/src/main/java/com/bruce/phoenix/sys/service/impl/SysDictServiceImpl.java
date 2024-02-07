package com.bruce.phoenix.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.bruce.phoenix.common.exception.CommonException;
import com.bruce.phoenix.sys.dao.SysDictDao;
import com.bruce.phoenix.sys.model.converter.SysDictConverter;
import com.bruce.phoenix.sys.model.form.SysDictForm;
import com.bruce.phoenix.sys.model.po.SysDict;
import com.bruce.phoenix.sys.model.vo.SysDictVO;
import com.bruce.phoenix.sys.service.SysDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright Copyright © 2022 Bruce . All rights reserved.
 * @Desc 系统数据字典service 实现类
 * @ProjectName demo
 * @Date 2022-6-20 9:50:39
 * @Author Bruce
 */
@Service
public class SysDictServiceImpl implements SysDictService {

    @Resource
    private SysDictDao dao;

    private static final SysDictConverter CONVERTER = new SysDictConverter();

    private static final Long TOP_ID = 0L;
    private static final String CODE = "code";
    private static final String P_CODE = "pCode";
    private static final String VALUE = "value";


    @Override
    public SysDict queryById(Long id) {
        return dao.queryById(id);
    }

    @Override
    public SysDict queryByCode(String code) {
        return dao.queryByCode(code);
    }

    @Override
    public List<SysDict> queryByPid(Long pId) {
        return dao.queryByPid(pId);
    }

    @Override
    public long save(SysDictForm form) {
        SysDict vo = queryByCode(form.getDictCode());
        if (vo != null) {
            throw new CommonException("code 不能重复");
        }
        SysDict po = new SysDict();
        CONVERTER.convert2Po(form, po);
        return dao.save(po);
    }

    @Override
    public void update(SysDictForm form) {
        SysDict vo = queryByCode(form.getDictCode());
        if (vo != null && !vo.getId().equals(form.getId())) {
            throw new CommonException("code 不能重复");
        }
        SysDict po = new SysDict();
        CONVERTER.convert2Po(form, po);
        dao.update(po);
    }

    @Override
    public List<SysDictVO> tree(String code) {
        List<SysDict> all;
        Long topId = TOP_ID;
        if (StrUtil.isBlank(code)) {
            all = queryAll();
        } else {
            SysDict top = queryByCode(code);
            topId = top.getPId();
            all = queryChildNode(top.getId());
        }

        List<Tree<Long>> tree = TreeUtil.build(all, topId, new TreeNodeConfig(), SysDictConverter::convert2Tree);
        return executeTree(tree);
    }

    private List<SysDictVO> executeTree(List<Tree<Long>> treeList) {
        if (CollUtil.isEmpty(treeList)) {
            return new ArrayList<>();
        }
        List<SysDictVO> result = new ArrayList<>();
        for (Tree<Long> tree : treeList) {
            SysDictVO sysDictVO = SysDictConverter.convert2VO(tree);
            List<SysDictVO> childList = executeTree(tree.getChildren());
            sysDictVO.setChildren(childList);
            result.add(sysDictVO);
        }
        return result;
    }

    @Override
    public List<SysDict> getRootPath(Long id) {
        List<SysDict> list = new ArrayList<>();
        SysDict sysDict = queryById(id);
        while (sysDict != null) {
            list.add(sysDict);
            sysDict = queryById(sysDict.getPId());
        }
        return list;
    }

    @Override
    public List<SysDict> queryChildNode(Long id) {
        SysDict top = queryById(id);
        List<SysDict> result = new ArrayList<>();

        // 递归获取
        queryChildNode(top, result);
        return result;
    }

    private void queryChildNode(SysDict sysDict, List<SysDict> result) {
        // 获取节点如果为空，返回
        if (sysDict == null) {
            return;
        }
        result.add(sysDict);
        List<SysDict> list = queryByPid(sysDict.getId());
        if (CollUtil.isNotEmpty(list)) {
            for (SysDict node : list) {
                queryChildNode(node, result);
            }
        }
    }


    @Override
    public List<SysDict> queryAll() {
        return dao.queryAll();
    }

    @Override
    public void refresh() {

    }

}
