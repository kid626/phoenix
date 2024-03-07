package com.bruce.phoenix.auth.dao;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bruce.phoenix.auth.mapper.RoleResourceMapper;
import com.bruce.phoenix.auth.model.po.RoleResource;
import com.bruce.phoenix.auth.model.query.RoleResourceQuery;
import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
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
public class RoleResourceDao {

    @Resource
    private RoleResourceMapper mapper;
    @Resource
    private IdGenerator idGenerator;

    /**
     * 新增
     */
    public Long save(RoleResource po) {
        long id = idGenerator.generateId().longValue();
        Date now = DateUtil.date();
        po.setId(id);
        mapper.insert(po);
        return id;
    }

    /**
     * 更新
     */
    public void update(RoleResource po) {
        mapper.updateById(po);
    }

    /**
     * 删除
     */
    public void remove(Long id) {
        mapper.deleteById(id);
    }

    /**
     * 按主键查询
     */
    public RoleResource queryById(Long id) {
        LambdaQueryWrapper<RoleResource> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(RoleResource::getId, id);
        wrapper.last("limit 1");
        return mapper.selectOne(wrapper);
    }


    /**
     * 按条件查询
     */
    public List<RoleResource> queryList(RoleResourceQuery query) {
        LambdaQueryWrapper<RoleResource> wrapper = Wrappers.lambdaQuery();
        return mapper.selectList(wrapper);
    }


    public List<RoleResource> queryByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleResource> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(RoleResource::getRoleId, roleId);
        return mapper.selectList(wrapper);
    }
}

