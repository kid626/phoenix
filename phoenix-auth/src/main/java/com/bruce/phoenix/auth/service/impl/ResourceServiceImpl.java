package com.bruce.phoenix.auth.service.impl;

import com.bruce.phoenix.auth.dao.ResourceDao;
import com.bruce.phoenix.auth.model.convert.ResourceConverter;
import com.bruce.phoenix.auth.model.enums.ResourceTypeEnum;
import com.bruce.phoenix.auth.model.form.ResourceForm;
import com.bruce.phoenix.auth.model.po.Resource;
import com.bruce.phoenix.auth.model.query.ResourceQuery;
import com.bruce.phoenix.auth.model.vo.ResourceVO;
import com.bruce.phoenix.auth.service.ResourceService;
import com.bruce.phoenix.common.converter.PageDataConverter;
import com.bruce.phoenix.common.model.common.PageData;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class ResourceServiceImpl implements ResourceService {

    @javax.annotation.Resource
    private ResourceDao dao;

    @Override
    public Long save(ResourceForm form) {
        Resource po = ResourceConverter.convert2Po(form);
        return dao.save(po);
    }

    @Override
    public void update(ResourceForm form) {
        Resource po = ResourceConverter.convert2Po(form);
        dao.update(po);
    }

    @Override
    public void remove(Long id) {
        dao.remove(id);
    }


    @Override
    public Resource queryById(Long id) {
        return dao.queryById(id);
    }

    @Override
    public PageData<ResourceVO> queryByPage(ResourceQuery query) {
        Page<Resource> pageInfo = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        try {
            List<Resource> list = dao.queryList(query);
            List<ResourceVO> result = list.stream().map(ResourceConverter::convert2Vo).collect(Collectors.toList());
            return PageDataConverter.convertFromPage(pageInfo, result);
        } finally {
            PageHelper.clearPage();
        }
    }

    @Override
    public List<Resource> getAvailableResources() {
        return dao.queryByType(ResourceTypeEnum.BUTTON.getCode());
    }

    @Override
    public List<Resource> queryAll() {
        return dao.queryList(new ResourceQuery());
    }

}
