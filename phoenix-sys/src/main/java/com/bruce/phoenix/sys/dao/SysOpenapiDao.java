package com.bruce.phoenix.sys.dao;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import com.bruce.phoenix.sys.model.po.SysOpenapi;
import com.bruce.phoenix.sys.model.query.SysOpenapiQuery;
import com.bruce.phoenix.sys.mapper.SysOpenapiMapper;
import com.bruce.phoenix.common.model.enums.YesOrNoEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Copyright Copyright © 2025 Bruce . All rights reserved.
 * @Desc Dao层
 * @ProjectName phoenix-sys
 * @Date 2025-06-27
 * @Author Bruce
 */
@Repository
@Slf4j
public class SysOpenapiDao {

    @Resource
    private SysOpenapiMapper mapper;
    @Resource
    private IdGenerator idGenerator;

    /**
     * 新增
     */
    public Long save(SysOpenapi po) {
        long id = idGenerator.generateId().longValue();
        Date now = DateUtil.date();
        po.setId(id);
        po.setCreateTime(now);
        po.setUpdateTime(now);
        po.setIsDelete(YesOrNoEnum.NO.getCode());
        mapper.insert(po);
        return id;
    }

    /**
     * 更新
     */
    public void update(SysOpenapi po) {
        Date now = DateUtil.date();
        po.setUpdateTime(now);
        mapper.updateById(po);
    }

    /**
     * 删除
     */
    public void remove(Long id) {
        SysOpenapi po = new SysOpenapi();
        Date now = DateUtil.date();
        po.setId(id);
        po.setUpdateTime(now);
        po.setIsDelete(YesOrNoEnum.YES.getCode());
        mapper.updateById(po);
    }

    /**
     * 按主键查询
     */
    public SysOpenapi queryById(Long id) {
        LambdaQueryWrapper<SysOpenapi> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysOpenapi::getIsDelete, YesOrNoEnum.NO.getCode());
        wrapper.eq(SysOpenapi::getId, id);
        wrapper.last("limit 1");
        return mapper.selectOne(wrapper);
    }


    /**
     * 按条件查询
     */
    public List<SysOpenapi> queryList(SysOpenapiQuery query) {
        LambdaQueryWrapper<SysOpenapi> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysOpenapi::getIsDelete, YesOrNoEnum.NO.getCode());
        // TODO 其他查询条件
        return mapper.selectList(wrapper);
    }


    public SysOpenapi queryByAppKey(String appKey) {
        LambdaQueryWrapper<SysOpenapi> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysOpenapi::getIsDelete, YesOrNoEnum.NO.getCode());
        wrapper.eq(SysOpenapi::getAccessKey, appKey);
        wrapper.last("limit 1");
        return mapper.selectOne(wrapper);
    }
}

