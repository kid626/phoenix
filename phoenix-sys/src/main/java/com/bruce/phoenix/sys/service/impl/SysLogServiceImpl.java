package com.bruce.phoenix.sys.service.impl;

import com.bruce.phoenix.common.converter.PageDataConverter;
import com.bruce.phoenix.common.model.common.PageData;
import com.bruce.phoenix.sys.dao.SysLogDao;
import com.bruce.phoenix.sys.model.converter.SysLogConverter;
import com.bruce.phoenix.sys.model.po.SysLog;
import com.bruce.phoenix.sys.model.query.SysLogQuery;
import com.bruce.phoenix.sys.model.vo.SysLogVO;
import com.bruce.phoenix.sys.service.SysLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Copyright Copyright © 2022 Bruce . All rights reserved.
 * @Desc 系统日志记录service 实现类
 * @ProjectName demo
 * @Date 2022-6-20 9:50:39
 * @Author Bruce
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogDao dao;

    private static final SysLogConverter CONVERTER = new SysLogConverter();

    @Override
    public void log(SysLog po) {
        dao.save(po);
    }

    @Override
    public PageData<SysLogVO> queryByPage(SysLogQuery query) {
        Page<SysLog> pageInfo = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        try {
            List<SysLog> list = dao.queryByPage(query);
            List<SysLogVO> result = list.stream().map((po) -> {
                SysLogVO vo = new SysLogVO();
                CONVERTER.convert2Vo(po, vo);
                return vo;
            }).collect(Collectors.toList());
            return PageDataConverter.convertFromPage(pageInfo, result);
        } finally {
            PageHelper.clearPage();
        }

    }
}
