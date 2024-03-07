package com.bruce.phoenix.auth.service;

import com.bruce.phoenix.auth.model.po.Resource;
import com.bruce.phoenix.auth.model.form.ResourceForm;
import com.bruce.phoenix.auth.model.vo.ResourceVO;
import com.bruce.phoenix.auth.model.query.ResourceQuery;
import com.bruce.phoenix.common.model.common.PageData;

import java.util.List;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc service 层
 * @ProjectName phoenix-auth
 * @Date 2024-03-05
 * @Author Bruce
 */
public interface ResourceService {

    /**
     * 新增
     *
     * @param form ResourceForm
     * @return 主键
     */
    Long save(ResourceForm form);


    /**
     * 更新
     *
     * @param form ResourceForm
     */
    void update(ResourceForm form);

    /**
     * 删除
     *
     * @param id 主键
     */
    void remove(Long id);

    /**
     * 按主键查询
     */
    Resource queryById(Long id);

    /**
     * 分页查询
     *
     * @param query ResourceQuery
     * @return 分页信息
     */
    PageData<ResourceVO> queryByPage(ResourceQuery query);

    /**
     * 获取所有按钮资源
     *
     * @return 所有按钮资源
     */
    List<Resource> getAvailableResources();

    /**
     * 获取所有资源
     *
     * @return  所有资源
     */
    List<Resource> queryAll();

}
