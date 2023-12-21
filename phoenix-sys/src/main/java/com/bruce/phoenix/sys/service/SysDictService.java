package com.bruce.phoenix.sys.service;

import com.bruce.phoenix.common.tree.BaseService;
import com.bruce.phoenix.sys.model.po.SysDict;
import com.bruce.phoenix.sys.model.tree.dict.SysDictForm;
import com.bruce.phoenix.sys.model.tree.dict.SysDictTree;
import com.bruce.phoenix.sys.model.tree.dict.SysDictVO;

import java.util.List;

/**
 * @Copyright Copyright © 2022 Bruce . All rights reserved.
 * @Desc 系统数据字典service 层
 * @ProjectName demo
 * @Date 2022-6-20 9:50:39
 * @Author Bruce
 */
public interface SysDictService extends BaseService<SysDictTree, SysDictForm, SysDictVO> {

    /**
     * 根据 id 查询
     *
     * @param id 主键
     * @return 数据字典详情
     */
    SysDict queryById(Long id);

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return 数据字典详情
     */
    SysDict queryByCode(String code);

    /**
     * 根据 父id 查询
     *
     * @param pId 父主键
     * @return 数据字典列表
     */
    List<SysDict> queryByPid(Long pId);

}
