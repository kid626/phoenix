package com.bruce.phoenix.sys.service.impl;

import com.bruce.phoenix.sys.dao.SysOpenapiDao;
import com.bruce.phoenix.sys.model.po.SysOpenapi;
import com.bruce.phoenix.sys.model.form.SysOpenapiForm;
import com.bruce.phoenix.sys.model.vo.SysOpenapiVO;
import com.bruce.phoenix.sys.model.query.SysOpenapiQuery;
import com.bruce.phoenix.sys.model.converter.SysOpenapiConverter;
import com.bruce.phoenix.sys.service.SysOpenapiService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.bruce.phoenix.common.model.common.PageData;
import com.bruce.phoenix.common.converter.PageDataConverter;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Copyright Copyright © 2025 Bruce . All rights reserved.
 * @Desc service 实现类
 * @ProjectName phoenix-sys
 * @Date 2025-06-27
 * @Author Bruce
 */
@Service
@Slf4j
public class SysOpenapiServiceImpl implements SysOpenapiService {

    @Resource
    private SysOpenapiDao dao;

    private static final SysOpenapiConverter CONVERTER = new SysOpenapiConverter();

    @Override
    public Long save(SysOpenapiForm form) {
        SysOpenapi po = new SysOpenapi();
        CONVERTER.convert2Po(form, po);
        return dao.save(po);
    }

    @Override
    public void update(SysOpenapiForm form) {
        SysOpenapi po = new SysOpenapi();
        CONVERTER.convert2Po(form, po);
        dao.update(po);
    }

    @Override
    public void remove(Long id) {
        dao.remove(id);
    }


    @Override
    public SysOpenapi queryById(Long id) {
        return dao.queryById(id);
    }

    @Override
    public PageData<SysOpenapiVO> queryByPage(SysOpenapiQuery query) {
        Page<SysOpenapi> pageInfo = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        try {
            List<SysOpenapi> list = dao.queryList(query);
            List<SysOpenapiVO> result = list.stream().map((po) -> {
                SysOpenapiVO vo = new SysOpenapiVO();
                CONVERTER.convert2Vo(po, vo);
                return vo;
            }).collect(Collectors.toList());
            return PageDataConverter.convertFromPage(pageInfo, result);
        } finally {
            PageHelper.clearPage();
        }
    }

    @Override
    public SysOpenapi queryByAccessKey(String appKey) {
        return dao.queryByAppKey(appKey);
    }

}
