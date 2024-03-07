package com.bruce.phoenix.auth.dao;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import com.bruce.phoenix.auth.model.po.Resource;
import com.bruce.phoenix.auth.model.query.ResourceQuery;
import com.bruce.phoenix.auth.mapper.ResourceMapper;
import com.bruce.phoenix.common.model.enums.YesOrNoEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc Dao层
 * @ProjectName phoenix-auth
 * @Date 2024-03-05
 * @Author Bruce
 */
@Repository
@Slf4j
public class ResourceDao {

    @javax.annotation.Resource
    private ResourceMapper mapper;
    @javax.annotation.Resource
    private IdGenerator idGenerator;

    /**
     * 新增
     */
    public Long save(Resource po) {
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
    public void update(Resource po) {
        Date now = DateUtil.date();
        po.setUpdateTime(now);
        mapper.updateById(po);
    }

    /**
     * 删除
     */
    public void remove(Long id) {
        Resource po = new Resource();
        Date now = DateUtil.date();
        po.setId(id);
        po.setUpdateTime(now);
        po.setIsDelete(YesOrNoEnum.YES.getCode());
        mapper.updateById(po);
    }

    /**
     * 按主键查询
     */
    public Resource queryById(Long id) {
        LambdaQueryWrapper<Resource> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Resource::getIsDelete, YesOrNoEnum.NO.getCode());
        wrapper.eq(Resource::getId, id);
        wrapper.last("limit 1");
        return mapper.selectOne(wrapper);
    }


    /**
     * 按条件查询
     */
    public List<Resource> queryList(ResourceQuery query) {
        LambdaQueryWrapper<Resource> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Resource::getIsDelete, YesOrNoEnum.NO.getCode());
        // TODO 其他查询条件
        return mapper.selectList(wrapper);
    }


    public List<Resource> queryByType(Integer type) {
        LambdaQueryWrapper<Resource> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Resource::getIsDelete, YesOrNoEnum.NO.getCode());
        wrapper.eq(Resource::getType, type);
        return mapper.selectList(wrapper);
    }
}

