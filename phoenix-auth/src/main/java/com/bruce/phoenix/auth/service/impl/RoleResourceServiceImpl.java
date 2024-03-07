package com.bruce.phoenix.auth.service.impl;

import com.bruce.phoenix.auth.dao.RoleResourceDao;
import com.bruce.phoenix.auth.model.converter.RoleResourceConverter;
import com.bruce.phoenix.auth.model.form.RoleResourceForm;
import com.bruce.phoenix.auth.model.po.Resource;
import com.bruce.phoenix.auth.model.po.RoleResource;
import com.bruce.phoenix.auth.model.query.RoleResourceQuery;
import com.bruce.phoenix.auth.model.vo.RoleResourceVO;
import com.bruce.phoenix.auth.service.ResourceService;
import com.bruce.phoenix.auth.service.RoleResourceService;
import com.bruce.phoenix.common.converter.PageDataConverter;
import com.bruce.phoenix.common.model.common.PageData;
import com.bruce.phoenix.common.model.enums.YesOrNoEnum;
import com.bruce.phoenix.core.model.security.AuthResource;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
public class RoleResourceServiceImpl implements RoleResourceService {

    @javax.annotation.Resource
    private RoleResourceDao dao;
    @javax.annotation.Resource
    private ResourceService resourceService;

    @Override
    public Long save(RoleResourceForm form) {
        RoleResource po = RoleResourceConverter.convert2Po(form);
        return dao.save(po);
    }

    @Override
    public void update(RoleResourceForm form) {
        RoleResource po = RoleResourceConverter.convert2Po(form);
        dao.update(po);
    }

    @Override
    public void remove(Long id) {
        dao.remove(id);
    }


    @Override
    public RoleResource queryById(Long id) {
        return dao.queryById(id);
    }

    @Override
    public PageData<RoleResourceVO> queryByPage(RoleResourceQuery query) {
        Page<RoleResource> pageInfo = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        try {
            List<RoleResource> list = dao.queryList(query);
            List<RoleResourceVO> result =
                    list.stream().map(RoleResourceConverter::convert2Vo).collect(Collectors.toList());
            return PageDataConverter.convertFromPage(pageInfo, result);
        } finally {
            PageHelper.clearPage();
        }
    }

    @Override
    public List<AuthResource> queryByRoleId(Long roleId) {
        List<RoleResource> roleResourceList = dao.queryByRoleId(roleId);
        Set<Long> resourceSet = roleResourceList.stream().map(RoleResource::getResourceId).collect(Collectors.toSet());
        List<AuthResource> resourceList = new ArrayList<>();
        for (Long resourceId : resourceSet) {
            Resource resource = resourceService.queryById(resourceId);
            if (resource != null && YesOrNoEnum.YES.getCode().equals(resource.getIsEnable())) {
                AuthResource authResource = new AuthResource();
                BeanUtils.copyProperties(resource, authResource);
                resourceList.add(authResource);
            }

        }
        return resourceList;
    }

}
