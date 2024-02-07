package com.bruce.phoenix.sys.dao;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bruce.phoenix.common.model.enums.YesOrNoEnum;
import com.bruce.phoenix.sys.mapper.SysConfigMapper;
import com.bruce.phoenix.sys.model.po.SysConfig;
import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/2/7 14:24
 * @Author Bruce
 */
@Repository
public class SysConfigDao {

    @Resource
    private SysConfigMapper mapper;
    @Resource
    private IdGenerator idGenerator;


    public List<SysConfig> queryAll() {
        LambdaQueryWrapper<SysConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysConfig::getIsDeleted, YesOrNoEnum.NO.getCode());
        return mapper.selectList(wrapper);
    }


    public SysConfig queryByCode(String code) {
        LambdaQueryWrapper<SysConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysConfig::getIsDeleted, YesOrNoEnum.NO.getCode());
        wrapper.eq(SysConfig::getConfigCode, code);
        wrapper.last("limit 1");
        return mapper.selectOne(wrapper);
    }

    public Long save(SysConfig po) {
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

    public void update(SysConfig po){
        Date date = DateUtil.date();
        po.setUpdateTime(date);
        mapper.updateById(po);
    }

    public void remove(Long id){
        SysConfig sysConfig = new SysConfig();
        Date date = DateUtil.date();
        sysConfig.setUpdateTime(date);
        sysConfig.setIsDeleted(YesOrNoEnum.YES.getCode());
        LambdaUpdateWrapper<SysConfig> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysConfig::getIsDeleted, YesOrNoEnum.NO.getCode())
                // 不能删除默认配置
                .eq(SysConfig::getIsDefault, YesOrNoEnum.NO.getCode())
                .eq(SysConfig::getId, id);
        mapper.update(sysConfig, wrapper);
    }


}
