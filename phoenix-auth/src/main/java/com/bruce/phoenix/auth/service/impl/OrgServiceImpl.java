package com.bruce.phoenix.auth.service.impl;

import com.bruce.phoenix.auth.dao.OrgDao;
import com.bruce.phoenix.auth.model.converter.OrgConverter;
import com.bruce.phoenix.auth.model.form.OrgForm;
import com.bruce.phoenix.auth.model.po.Org;
import com.bruce.phoenix.auth.model.query.OrgQuery;
import com.bruce.phoenix.auth.model.vo.OrgVO;
import com.bruce.phoenix.auth.service.OrgService;
import com.bruce.phoenix.common.converter.PageDataConverter;
import com.bruce.phoenix.common.model.common.PageData;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 组织service 实现类
 * @ProjectName phoenix-auth
 * @Date 2024-03-07
 * @Author Bruce
 */
@Service
@Slf4j
public class OrgServiceImpl implements OrgService {

    @Resource
    private OrgDao dao;

    private static final OrgConverter CONVERTER = new OrgConverter();

    @Override
    public Long save(OrgForm form) {
        Org po = new Org();
        CONVERTER.convert2Po(form, po);
        return dao.save(po);
    }

    @Override
    public void update(OrgForm form) {
        Org po = new Org();
        CONVERTER.convert2Po(form, po);
        dao.update(po);
    }

    @Override
    public void remove(Long id) {
        dao.remove(id);
    }


    @Override
    public Org queryById(Long id) {
        return dao.queryById(id);
    }

    @Override
    public PageData<OrgVO> queryByPage(OrgQuery query) {
        Page<Org> pageInfo = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        try {
            List<Org> list = dao.queryList(query);
            List<OrgVO> result = list.stream().map((po) -> {
                OrgVO vo = new OrgVO();
                CONVERTER.convert2Vo(po, vo);
                return vo;
            }).collect(Collectors.toList());
            return PageDataConverter.convertFromPage(pageInfo, result);
        } finally {
            PageHelper.clearPage();
        }
    }

}
