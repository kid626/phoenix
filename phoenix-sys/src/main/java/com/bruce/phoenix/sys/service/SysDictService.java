package com.bruce.phoenix.sys.service;

import com.bruce.phoenix.sys.model.form.SysDictForm;
import com.bruce.phoenix.sys.model.po.SysDict;
import com.bruce.phoenix.sys.model.vo.SysDictVO;

import java.util.List;

/**
 * @Copyright Copyright © 2022 Bruce . All rights reserved.
 * @Desc 系统数据字典service 层
 * @ProjectName demo
 * @Date 2022-6-20 9:50:39
 * @Author Bruce
 */
public interface SysDictService {

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

    /**
     * 新增
     *
     * @param form TreeForm
     * @return 主键
     */
    long save(SysDictForm form);

    /**
     * 修改
     *
     * @param form TreeForm
     */
    void update(SysDictForm form);

    /**
     * 生成树
     *
     * @return TreeVo
     */
    List<SysDictVO> tree(String code);

    /**
     * 获取当前节点到根节点的路径
     *
     * @param id 某个节点主键
     * @return 返回该节点到根节点的路径
     */
    List<SysDict> getRootPath(Long id);


    /**
     * 获取当前节点及其所有子节点
     * 根据 code 来，code 做右模糊查询
     *
     * @param id 某个节点主键
     * @return 返回该节点及其所欲子节点
     */
    List<SysDict> queryChildNode(Long id);

    /**
     * 获取全部
     *
     * @return 全部组织信息
     */
    List<SysDict> queryAll();

    /**
     * 刷新缓存
     */
    void refresh();

}
