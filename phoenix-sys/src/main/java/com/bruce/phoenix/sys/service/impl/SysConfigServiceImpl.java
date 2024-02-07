package com.bruce.phoenix.sys.service.impl;

import com.bruce.phoenix.common.converter.PageDataConverter;
import com.bruce.phoenix.common.exception.CommonException;
import com.bruce.phoenix.common.model.common.BasePageQuery;
import com.bruce.phoenix.common.model.common.PageData;
import com.bruce.phoenix.sys.dao.SysConfigDao;
import com.bruce.phoenix.sys.model.converter.SysConfigConverter;
import com.bruce.phoenix.sys.model.form.SysConfigForm;
import com.bruce.phoenix.sys.model.po.SysConfig;
import com.bruce.phoenix.sys.model.vo.SysConfigVO;
import com.bruce.phoenix.sys.service.SysConfigService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    private SysConfigDao dao;

    private static final SysConfigConverter CONVERTER = new SysConfigConverter();

    @Override
    public List<SysConfigVO> queryAll() {
        List<SysConfig> sysConfigList = dao.queryAll();
        List<SysConfigVO> result = Lists.newArrayList();
        for (SysConfig sysConfig : sysConfigList) {
            SysConfigVO vo = new SysConfigVO();
            CONVERTER.convert2Vo(sysConfig, vo);
            result.add(vo);
        }
        return result;
    }

    @Override
    public PageData<SysConfigVO> queryAll(BasePageQuery query) {
        Page<SysConfig> pageInfo = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        try {
            List<SysConfigVO> list = queryAll();
            return PageDataConverter.convertFromPage(pageInfo, list);
        } finally {
            PageHelper.clearPage();
        }
    }

    @Override
    public SysConfigVO queryByCode(String code) {
        SysConfig sysConfig = dao.queryByCode(code);
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
        return dao.save(sysConfig);
    }

    @Override
    public void update(SysConfigForm form) {
        SysConfigVO vo = queryByCode(form.getConfigCode());
        if (vo != null && !vo.getId().equals(form.getId())) {
            throw new CommonException("code 不能重复");
        }
        SysConfig sysConfig = new SysConfig();
        CONVERTER.convert2Po(form, sysConfig);
        dao.update(sysConfig);
    }

    @Override
    public void remove(Long id) {
        dao.remove(id);
    }
}
