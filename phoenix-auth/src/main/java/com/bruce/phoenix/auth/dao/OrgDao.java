package com.bruce.phoenix.auth.dao;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import com.bruce.phoenix.auth.model.po.Org;
import com.bruce.phoenix.auth.model.query.OrgQuery;
import com.bruce.phoenix.auth.mapper.OrgMapper;
import com.bruce.phoenix.common.model.enums.YesOrNoEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 组织Dao层
 * @ProjectName phoenix-auth
 * @Date 2024-03-07
 * @Author Bruce
 */
@Repository
@Slf4j
public class OrgDao {

    @Resource
    private OrgMapper mapper;
    @Resource
    private IdGenerator idGenerator;

    /**
     * 新增
     */
    public Long save(Org po) {
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
    public void update(Org po) {
        Date now = DateUtil.date();
        po.setUpdateTime(now);
        mapper.updateById(po);
    }

    /**
     * 删除
     */
    public void remove(Long id) {
        Org po = new Org();
        Date now = DateUtil.date();
        po.setId(id);
        po.setUpdateTime(now);
        po.setIsDelete(YesOrNoEnum.YES.getCode());
        mapper.updateById(po);
    }

    /**
     * 按主键查询
     */
    public Org queryById(Long id) {
        LambdaQueryWrapper<Org> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Org::getIsDelete, YesOrNoEnum.NO.getCode());
        wrapper.eq(Org::getId, id);
        wrapper.last("limit 1");
        return mapper.selectOne(wrapper);
    }


    /**
     * 按条件查询
     */
    public List<Org> queryList(OrgQuery query) {
        LambdaQueryWrapper<Org> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Org::getIsDelete, YesOrNoEnum.NO.getCode());
        // TODO 其他查询条件
        return mapper.selectList(wrapper);
    }



}

