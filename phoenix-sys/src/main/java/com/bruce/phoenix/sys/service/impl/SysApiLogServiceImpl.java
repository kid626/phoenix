package com.bruce.phoenix.sys.service.impl;

import com.bruce.phoenix.sys.dao.SysApiLogDao;
import com.bruce.phoenix.sys.model.po.SysApiLog;
import com.bruce.phoenix.sys.model.form.SysApiLogForm;
import com.bruce.phoenix.sys.model.vo.SysApiLogVO;
import com.bruce.phoenix.sys.model.query.SysApiLogQuery;
import com.bruce.phoenix.sys.model.converter.SysApiLogConverter;
import com.bruce.phoenix.sys.service.SysApiLogService;
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
 * @Desc 系统第三方请求日志service 实现类
 * @ProjectName phoenix-sys
 * @Date 2024-03-01
 * @Author Bruce
 */
@Service
@Slf4j
public class SysApiLogServiceImpl implements SysApiLogService {

    @Resource
    private SysApiLogDao dao;

    @Override
    public Long save(SysApiLogForm form) {
        SysApiLog po = SysApiLogConverter.convert2Po(form);
        return dao.save(po);
    }

    @Override
    public void update(SysApiLogForm form) {
        SysApiLog po = SysApiLogConverter.convert2Po(form);
        dao.update(po);
    }

    @Override
    public void remove(Long id) {
        dao.remove(id);
    }


    @Override
    public SysApiLog queryById(Long id) {
        return dao.queryById(id);
    }

    @Override
    public PageData<SysApiLogVO> queryByPage(SysApiLogQuery query) {
        Page<SysApiLog> pageInfo = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        try {
            List<SysApiLog> list = dao.queryList(query);
            List<SysApiLogVO> result = list.stream().map(SysApiLogConverter::convert2Vo).collect(Collectors.toList());
            return PageDataConverter.convertFromPage(pageInfo, result);
        } finally {
            PageHelper.clearPage();
        }
    }

}
