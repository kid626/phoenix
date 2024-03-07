package com.bruce.phoenix.auth.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bruce.phoenix.auth.mapper.UserOrgMapper;
import com.bruce.phoenix.auth.model.po.UserOrg;
import com.bruce.phoenix.auth.model.query.UserOrgQuery;
import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 用户-组织关系Dao层
 * @ProjectName phoenix-auth
 * @Date 2024-03-07
 * @Author Bruce
 */
@Repository
@Slf4j
public class UserOrgDao {

    @Resource
    private UserOrgMapper mapper;
    @Resource
    private IdGenerator idGenerator;

    /**
     * 新增
     */
    public Long save(UserOrg po) {
        long id = idGenerator.generateId().longValue();
        po.setId(id);
        mapper.insert(po);
        return id;
    }

    /**
     * 更新
     */
    public void update(UserOrg po) {
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
    public UserOrg queryById(Long id) {
        LambdaQueryWrapper<UserOrg> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserOrg::getId, id);
        wrapper.last("limit 1");
        return mapper.selectOne(wrapper);
    }


    /**
     * 按条件查询
     */
    public List<UserOrg> queryList(UserOrgQuery query) {
        LambdaQueryWrapper<UserOrg> wrapper = Wrappers.lambdaQuery();
        return mapper.selectList(wrapper);
    }



}

