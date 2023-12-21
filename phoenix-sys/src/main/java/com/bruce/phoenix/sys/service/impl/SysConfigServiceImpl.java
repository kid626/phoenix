package com.bruce.phoenix.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bruce.phoenix.common.exception.CommonException;
import com.bruce.phoenix.common.model.enums.YesOrNoEnum;
import com.bruce.phoenix.sys.mapper.SysConfigMapper;
import com.bruce.phoenix.sys.model.converter.SysConfigConverter;
import com.bruce.phoenix.sys.model.form.SysConfigForm;
import com.bruce.phoenix.sys.model.po.SysConfig;
import com.bruce.phoenix.sys.model.vo.SysConfigVO;
import com.bruce.phoenix.sys.service.SysConfigService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Copyright Copyright © 2022 Bruce . All rights reserved.
 * @Desc 系统配置表service 实现类
 * @ProjectName demo
 * @Date 2022-6-20 9:50:39
 * @Author Bruce
 */
@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Resource
    private SysConfigMapper mapper;

    private static final SysConfigConverter CONVERTER = new SysConfigConverter();

    @Override
    public List<SysConfigVO> queryAll() {
        LambdaQueryWrapper<SysConfig> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(SysConfig::getIsDeleted, YesOrNoEnum.NO.getCode());
        List<SysConfig> sysConfigList = mapper.selectList(lambdaQuery);
        List<SysConfigVO> result = Lists.newArrayList();
        for (SysConfig sysConfig : sysConfigList) {
            SysConfigVO vo = new SysConfigVO();
            CONVERTER.convert2Vo(sysConfig, vo);
            result.add(vo);
        }
        return result;
    }

    @Override
    public SysConfigVO queryByCode(String code) {
        LambdaQueryWrapper<SysConfig> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(SysConfig::getIsDeleted, YesOrNoEnum.NO.getCode())
                .eq(SysConfig::getConfigCode, code);
        SysConfig sysConfig = mapper.selectOne(lambdaQuery);
        if (sysConfig != null) {
            SysConfigVO vo = new SysConfigVO();
            CONVERTER.convert2Vo(sysConfig, vo);
            return vo;
        }
        return null;
    }

    @Override
    public long save(SysConfigForm form) {
        SysConfigVO vo = queryByCode(form.getConfigCode());
        if (vo != null) {
            throw new CommonException("code 不能重复");
        }
        SysConfig sysConfig = new SysConfig();
        CONVERTER.convert2Po(form, sysConfig);
        Date date = new Date();
        sysConfig.setCreateTime(date);
        sysConfig.setUpdateTime(date);
        // 只能新增非默认配置
        sysConfig.setIsDefault(YesOrNoEnum.NO.getCode());
        sysConfig.setIsDeleted(YesOrNoEnum.NO.getCode());
        sysConfig.setIsEnable(YesOrNoEnum.YES.getCode());
        mapper.insert(sysConfig);
        return sysConfig.getId();
    }

    @Override
    public void update(SysConfigForm form) {
        SysConfigVO vo = queryByCode(form.getConfigCode());
        if (vo != null && !vo.getId().equals(form.getId())) {
            throw new CommonException("code 不能重复");
        }
        SysConfig sysConfig = new SysConfig();
        CONVERTER.convert2Po(form, sysConfig);
        Date date = new Date();
        sysConfig.setUpdateTime(date);
        mapper.updateById(sysConfig);
    }

    @Override
    public void remove(Long id) {
        SysConfig sysConfig = new SysConfig();
        Date date = new Date();
        sysConfig.setUpdateTime(date);
        sysConfig.setIsDeleted(YesOrNoEnum.YES.getCode());
        LambdaUpdateWrapper<SysConfig> lambdaUpdate = Wrappers.lambdaUpdate();
        lambdaUpdate.eq(SysConfig::getIsDeleted, YesOrNoEnum.NO.getCode())
                // 不能删除默认配置
                .eq(SysConfig::getIsDefault, YesOrNoEnum.NO.getCode())
                .eq(SysConfig::getId, id);
        mapper.update(sysConfig, lambdaUpdate);
    }
}
