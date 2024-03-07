package com.bruce.phoenix.auth.service.impl;

import com.bruce.phoenix.auth.dao.UserOrgDao;
import com.bruce.phoenix.auth.model.po.UserOrg;
import com.bruce.phoenix.auth.model.form.UserOrgForm;
import com.bruce.phoenix.auth.model.vo.UserOrgVO;
import com.bruce.phoenix.auth.model.query.UserOrgQuery;
import com.bruce.phoenix.auth.model.converter.UserOrgConverter;
import com.bruce.phoenix.auth.service.UserOrgService;
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
 * @Desc 用户-组织关系service 实现类
 * @ProjectName phoenix-auth
 * @Date 2024-03-07
 * @Author Bruce
 */
@Service
@Slf4j
public class UserOrgServiceImpl implements UserOrgService {

    @Resource
    private UserOrgDao dao;

    private static final UserOrgConverter CONVERTER = new UserOrgConverter();

    @Override
    public Long save(UserOrgForm form) {
        UserOrg po = new UserOrg();
        CONVERTER.convert2Po(form, po);
        return dao.save(po);
    }

    @Override
    public void update(UserOrgForm form) {
        UserOrg po = new UserOrg();
        CONVERTER.convert2Po(form, po);
        dao.update(po);
    }

    @Override
    public void remove(Long id) {
        dao.remove(id);
    }


    @Override
    public UserOrg queryById(Long id) {
        return dao.queryById(id);
    }

    @Override
    public PageData<UserOrgVO> queryByPage(UserOrgQuery query) {
        Page<UserOrg> pageInfo = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        try {
            List<UserOrg> list = dao.queryList(query);
            List<UserOrgVO> result = list.stream().map((po) -> {
                UserOrgVO vo = new UserOrgVO();
                CONVERTER.convert2Vo(po, vo);
                return vo;
            }).collect(Collectors.toList());
            return PageDataConverter.convertFromPage(pageInfo, result);
        } finally {
            PageHelper.clearPage();
        }
    }

}
