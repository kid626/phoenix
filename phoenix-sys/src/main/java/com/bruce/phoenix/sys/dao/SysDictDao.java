package com.bruce.phoenix.sys.dao;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bruce.phoenix.common.model.enums.YesOrNoEnum;
import com.bruce.phoenix.sys.mapper.SysDictMapper;
import com.bruce.phoenix.sys.model.po.SysDict;
import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/2/7 14:42
 * @Author Bruce
 */
@Repository
public class SysDictDao {

    @Resource
    private SysDictMapper mapper;
    @Resource
    private IdGenerator idGenerator;

    public SysDict queryById(Long id) {
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDict::getId, id).eq(SysDict::getIsDeleted, YesOrNoEnum.NO.getCode());
        wrapper.last("limit 1");
        return mapper.selectOne(wrapper);
    }

    public SysDict queryByCode(String code) {
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDict::getDictCode, code).eq(SysDict::getIsDeleted, YesOrNoEnum.NO.getCode());
        wrapper.last("limit 1");
        return mapper.selectOne(wrapper);
    }

    public List<SysDict> queryByPid(Long pId) {
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDict::getPId, pId).eq(SysDict::getIsDeleted, YesOrNoEnum.NO.getCode());
        return mapper.selectList(wrapper);
    }

    public Long save(SysDict po) {
        long id = idGenerator.generateId().longValue();
        po.setId(id);
        Date date = DateUtil.date();
        po.setCreateTime(date);
        po.setUpdateTime(date);
        // 只能新增非默认配置
        po.setIsDefault(YesOrNoEnum.NO.getCode());
        po.setIsDeleted(YesOrNoEnum.NO.getCode());
        po.setIsEnable(YesOrNoEnum.YES.getCode());
        mapper.insert(po);
        return po.getId();
    }

    public void update(SysDict po) {
        Date date = DateUtil.date();
        po.setUpdateTime(date);
        mapper.updateById(po);
    }

    public List<SysDict> queryAll() {
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDict::getIsDeleted, YesOrNoEnum.NO.getCode());
        return mapper.selectList(wrapper);

    }
}
