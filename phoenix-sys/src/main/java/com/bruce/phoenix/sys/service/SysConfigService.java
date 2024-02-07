package com.bruce.phoenix.sys.service;


import com.bruce.phoenix.common.model.common.BasePageQuery;
import com.bruce.phoenix.common.model.common.PageData;
import com.bruce.phoenix.sys.model.form.SysConfigForm;
import com.bruce.phoenix.sys.model.vo.SysConfigVO;

import java.util.List;

/**
 * @Copyright Copyright © 2022 Bruce . All rights reserved.
 * @Desc 系统配置表service 层
 * @ProjectName demo
 * @Date 2022-6-20 9:50:39
 * @Author Bruce
 */
public interface SysConfigService {

    /**
     * 查询全部
     *
     * @return 全部系统配置
     */
    List<SysConfigVO> queryAll();

    /**
     * 查询全部
     *
     * @return 全部系统配置
     */
    PageData<SysConfigVO> queryAll(BasePageQuery query);

    /**
     * 根据 code 编码获取
     *
     * @param code 唯一编码
     */
    SysConfigVO queryByCode(String code);

    /**
     * 保存一条配置
     *
     * @param form SysConfigForm
     * @return 主键
     */
    long save(SysConfigForm form);

    /**
     * 更新系统配置
     *
     * @param form SysConfigForm
     */
    void update(SysConfigForm form);

    /**
     * 删除一条系统配置
     *
     * @param id 主键
     */
    void remove(Long id);

}
