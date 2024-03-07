package com.bruce.phoenix.auth.service.impl;

import com.bruce.phoenix.auth.dao.RoleDao;
import com.bruce.phoenix.auth.model.po.Role;
import com.bruce.phoenix.auth.model.form.RoleForm;
import com.bruce.phoenix.auth.model.vo.RoleVO;
import com.bruce.phoenix.auth.model.query.RoleQuery;
import com.bruce.phoenix.auth.model.converter.RoleConverter;
import com.bruce.phoenix.auth.service.RoleService;
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
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc service 实现类
 * @ProjectName phoenix-auth
 * @Date 2024-03-05
 * @Author Bruce
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao dao;

    @Override
    public Long save(RoleForm form) {
        Role po = RoleConverter.convert2Po(form);
        return dao.save(po);
    }

    @Override
    public void update(RoleForm form) {
        Role po = RoleConverter.convert2Po(form);
        dao.update(po);
    }

    @Override
    public void remove(Long id) {
        dao.remove(id);
    }


    @Override
    public Role queryById(Long id) {
        return dao.queryById(id);
    }

    @Override
    public PageData<RoleVO> queryByPage(RoleQuery query) {
        Page<Role> pageInfo = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        try {
            List<Role> list = dao.queryList(query);
            List<RoleVO> result = list.stream().map(RoleConverter::convert2Vo).collect(Collectors.toList());
            return PageDataConverter.convertFromPage(pageInfo, result);
        } finally {
            PageHelper.clearPage();
        }
    }

}
