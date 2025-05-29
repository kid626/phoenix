package com.bruce.demo.web.service.impl;

import com.bruce.demo.web.dao.DemoUserDao;
import com.bruce.demo.web.model.po.DemoUser;
import com.bruce.demo.web.model.form.DemoUserForm;
import com.bruce.demo.web.model.vo.DemoUserVO;
import com.bruce.demo.web.model.query.DemoUserQuery;
import com.bruce.demo.web.model.convert.DemoUserConverter;
import com.bruce.demo.web.service.DemoUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.bruce.phoenix.common.model.common.PageData;
import com.bruce.phoenix.common.converter.PageDataConverter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Copyright Copyright © 2023 Bruce . All rights reserved.
 * @Desc service 实现类
 * @ProjectName phoenix-demo
 * @Date 2023-12-19
 * @Author Bruce
 */
@Service
@Slf4j
public class DemoUserServiceImpl implements DemoUserService {

    @Resource
    private DemoUserDao dao;

    @Override
    public Long save(DemoUserForm form) {
        DemoUser po = DemoUserConverter.convert2Po(form);
        return dao.save(po);
    }

    @Override
    public void update(DemoUserForm form) {
        DemoUser po = DemoUserConverter.convert2Po(form);
        dao.update(po);
    }

    @Override
    public void remove(Long id) {
        dao.remove(id);
    }


    @Override
    public DemoUser queryById(Long id) {
        return dao.queryById(id);
    }

    @Override
    public PageData<DemoUserVO> queryByPage(DemoUserQuery query) {
        Page<DemoUser> pageInfo = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        try {
            List<DemoUser> list = dao.queryList(query);
            List<DemoUserVO> result = list.stream().map(DemoUserConverter::convert2Vo).collect(Collectors.toList());
            return PageDataConverter.convertFromPage(pageInfo, result);
        } finally {
            PageHelper.clearPage();
        }
    }

    @Override
    public void batchSave() {
        List<DemoUser> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            DemoUser demoUser = new DemoUser();
            demoUser.setId((long) i);
            demoUser.setName("test" + i);
            demoUser.setDeptId((long) i);
            demoUser.setGrade(1);
            list.add(demoUser);
        }
        dao.batchSave(list);
    }

}
