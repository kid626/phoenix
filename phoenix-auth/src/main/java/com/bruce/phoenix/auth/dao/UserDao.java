package com.bruce.phoenix.auth.dao;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import com.bruce.phoenix.auth.model.po.User;
import com.bruce.phoenix.auth.model.query.UserQuery;
import com.bruce.phoenix.auth.mapper.UserMapper;
import com.bruce.phoenix.common.model.enums.YesOrNoEnum;
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
public class UserDao {

    @Resource
    private UserMapper mapper;
    @Resource
    private IdGenerator idGenerator;

    /**
     * 新增
     */
    public Long save(User po) {
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
    public void update(User po) {
        Date now = DateUtil.date();
        po.setUpdateTime(now);
        mapper.updateById(po);
    }

    /**
     * 删除
     */
    public void remove(Long id) {
        User po = new User();
        Date now = DateUtil.date();
        po.setId(id);
        po.setUpdateTime(now);
        po.setIsDelete(YesOrNoEnum.YES.getCode());
        mapper.updateById(po);
    }

    /**
     * 按主键查询
     */
    public User queryById(Long id) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getIsDelete, YesOrNoEnum.NO.getCode());
        wrapper.eq(User::getId, id);
        wrapper.last("limit 1");
        return mapper.selectOne(wrapper);
    }


    /**
     * 按条件查询
     */
    public List<User> queryList(UserQuery query) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getIsDelete, YesOrNoEnum.NO.getCode());
        // TODO 其他查询条件
        return mapper.selectList(wrapper);
    }


    public User queryByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getIsDelete, YesOrNoEnum.NO.getCode());
        wrapper.eq(User::getUsername, username);
        wrapper.eq(User::getIsEnable, YesOrNoEnum.YES.getCode());
        wrapper.last("limit 1");
        return mapper.selectOne(wrapper);
    }
}

