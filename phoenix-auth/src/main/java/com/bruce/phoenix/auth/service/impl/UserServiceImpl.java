package com.bruce.phoenix.auth.service.impl;

import com.bruce.phoenix.auth.dao.UserDao;
import com.bruce.phoenix.auth.model.po.User;
import com.bruce.phoenix.auth.model.form.UserForm;
import com.bruce.phoenix.auth.model.vo.UserVO;
import com.bruce.phoenix.auth.model.query.UserQuery;
import com.bruce.phoenix.auth.model.convert.UserConverter;
import com.bruce.phoenix.auth.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao dao;

    @Override
    public Long save(UserForm form) {
        User po = UserConverter.convert2Po(form);
        return dao.save(po);
    }

    @Override
    public void update(UserForm form) {
        User po = UserConverter.convert2Po(form);
        dao.update(po);
    }

    @Override
    public void remove(Long id) {
        dao.remove(id);
    }


    @Override
    public User queryById(Long id) {
        return dao.queryById(id);
    }

    @Override
    public PageData<UserVO> queryByPage(UserQuery query) {
        Page<User> pageInfo = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        try {
            List<User> list = dao.queryList(query);
            List<UserVO> result = list.stream().map(UserConverter::convert2Vo).collect(Collectors.toList());
            return PageDataConverter.convertFromPage(pageInfo, result);
        } finally {
            PageHelper.clearPage();
        }
    }

    @Override
    public User queryByUsername(String username) {
        return dao.queryByUsername(username);
    }

}
