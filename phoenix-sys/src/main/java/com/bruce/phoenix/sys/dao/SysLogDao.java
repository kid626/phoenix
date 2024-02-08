package com.bruce.phoenix.sys.dao;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bruce.phoenix.common.model.enums.YesOrNoEnum;
import com.bruce.phoenix.sys.mapper.SysLogMapper;
import com.bruce.phoenix.sys.model.po.SysLog;
import com.bruce.phoenix.sys.model.query.SysLogQuery;
import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/2/7 19:26
 * @Author Bruce
 */
@Repository
public class SysLogDao {

    @Resource
    private SysLogMapper mapper;
    @Resource
    private IdGenerator idGenerator;

    public Long save(SysLog po) {
        long id = idGenerator.generateId().longValue();
        po.setId(id);
        Date date = DateUtil.date();
        po.setCreateTime(date);
        po.setIsDeleted(YesOrNoEnum.NO.getCode());
        mapper.insert(po);
        return po.getId();
    }


    public List<SysLog> queryByPage(SysLogQuery query) {
        LambdaQueryWrapper<SysLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysLog::getIsDeleted, YesOrNoEnum.NO.getCode());
        if (StrUtil.isNotBlank(query.getBusinessType())) {
            wrapper.eq(SysLog::getBusinessType, query.getBusinessType());
        }
        if (StrUtil.isNotBlank(query.getMethod())) {
            wrapper.like(SysLog::getMethod, query.getMethod());
        }
        if (StrUtil.isNotBlank(query.getStatus())) {
            wrapper.eq(SysLog::getStatus, query.getStatus());
        }
        if (StrUtil.isNotBlank(query.getOperatorType())) {
            wrapper.eq(SysLog::getOperatorType, query.getOperatorType());
        }
        if (StrUtil.isNotBlank(query.getRequestMethod())) {
            wrapper.eq(SysLog::getRequestMethod, query.getRequestMethod());
        }
        wrapper.orderByDesc(SysLog::getCreateTime);
        return mapper.selectList(wrapper);
    }
}
