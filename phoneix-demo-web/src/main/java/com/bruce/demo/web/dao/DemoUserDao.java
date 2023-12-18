package com.bruce.demo.web.dao;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import com.bruce.demo.web.model.po.DemoUser;
import com.bruce.demo.web.model.query.DemoUserQuery;
import com.bruce.demo.web.mapper.DemoUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Copyright Copyright © 2023 Bruce . All rights reserved.
 * @Desc Dao层
 * @ProjectName phoenix-demo
 * @Date 2023-12-18
 * @Author Bruce
 */
@Repository
@Slf4j
public class DemoUserDao {

    @Resource
    private DemoUserMapper mapper;
    @Resource
    private IdGenerator idGenerator;

    /**
     * 新增
     */
    public Long save(DemoUser po) {
        long id = idGenerator.generateId().longValue();
        Date now = DateUtil.date();
        po.setId(id);
        po.setCreateTime(now);
        po.setUpdateTime(now);
        po.setIsDelete("N");
        mapper.insert(po);
        return id;
    }

    /**
     * 更新
     */
    public void update(DemoUser po) {
        Date now = DateUtil.date();
        po.setUpdateTime(now);
        mapper.updateById(po);
    }

    /**
     * 删除
     */
    public void remove(Long id) {
        DemoUser po = new DemoUser();
        Date now = DateUtil.date();
        po.setUpdateTime(now);
        po.setIsDelete("Y");
        mapper.updateById(po);
    }

    /**
     * 按主键查询
     */
    public DemoUser queryById(Long id) {
        LambdaQueryWrapper<DemoUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DemoUser::getIsDelete, "N");
        wrapper.eq(DemoUser::getId, id);
        wrapper.last("limit 1");
        return mapper.selectOne(wrapper);
    }


    /**
     * 按条件查询
     */
    public List<DemoUser> queryList(DemoUserQuery query) {
        LambdaQueryWrapper<DemoUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DemoUser::getIsDelete, "N");
        // TODO 其他查询条件
        return mapper.selectList(wrapper);
    }



}

