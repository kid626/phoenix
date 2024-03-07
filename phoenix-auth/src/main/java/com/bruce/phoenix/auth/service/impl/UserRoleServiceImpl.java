package com.bruce.phoenix.auth.service.impl;

import com.bruce.phoenix.auth.dao.UserRoleDao;
import com.bruce.phoenix.auth.model.convert.UserRoleConverter;
import com.bruce.phoenix.auth.model.form.UserRoleForm;
import com.bruce.phoenix.auth.model.po.Role;
import com.bruce.phoenix.auth.model.po.UserRole;
import com.bruce.phoenix.auth.model.query.UserRoleQuery;
import com.bruce.phoenix.auth.model.vo.UserRoleVO;
import com.bruce.phoenix.auth.service.RoleService;
import com.bruce.phoenix.auth.service.UserRoleService;
import com.bruce.phoenix.common.converter.PageDataConverter;
import com.bruce.phoenix.common.model.common.PageData;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    private UserRoleDao dao;
    @Resource
    private RoleService roleService;

    @Override
    public Long save(UserRoleForm form) {
        UserRole po = UserRoleConverter.convert2Po(form);
        return dao.save(po);
    }

    @Override
    public void update(UserRoleForm form) {
        UserRole po = UserRoleConverter.convert2Po(form);
        dao.update(po);
    }

    @Override
    public void remove(Long id) {
        dao.remove(id);
    }


    @Override
    public UserRole queryById(Long id) {
        return dao.queryById(id);
    }

    @Override
    public PageData<UserRoleVO> queryByPage(UserRoleQuery query) {
        Page<UserRole> pageInfo = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        try {
            List<UserRole> list = dao.queryList(query);
            List<UserRoleVO> result = list.stream().map(UserRoleConverter::convert2Vo).collect(Collectors.toList());
            return PageDataConverter.convertFromPage(pageInfo, result);
        } finally {
            PageHelper.clearPage();
        }
    }

    @Override
    public List<Role> queryByUserId(Long userId) {
        List<UserRole> userRoleList = dao.queryByUserId(userId);
        Set<Long> roleSet = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        List<Role> roleList = new ArrayList<>();
        for (Long roleId : roleSet) {
            Role role = roleService.queryById(roleId);
            roleList.add(role);
        }
        return roleList;
    }

}
