package com.bruce.phoenix.sys.dao;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bruce.phoenix.sys.mapper.SysApiLogMapper;
import com.bruce.phoenix.sys.model.po.SysApiLog;
import com.bruce.phoenix.sys.model.query.SysApiLogQuery;
import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 系统第三方请求日志Dao层
 * @ProjectName phoenix-sys
 * @Date 2024-03-01
 * @Author Bruce
 */
@Repository
@Slf4j
public class SysApiLogDao {

    @Resource
    private SysApiLogMapper mapper;
    @Resource
    private IdGenerator idGenerator;

    /**
     * 新增
     */
    public Long save(SysApiLog po) {
        long id = idGenerator.generateId().longValue();
        po.setId(id);
        mapper.insert(po);
        return id;
    }

    /**
     * 更新
     */
    public void update(SysApiLog po) {
        LambdaUpdateWrapper<SysApiLog> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysApiLog::getRequestId, po.getRequestId());
        wrapper.set(SysApiLog::getResponseTime, DateUtil.date());
        wrapper.set(SysApiLog::getResponseBody, po.getResponseBody());
        wrapper.set(SysApiLog::getResponseCode, po.getResponseCode());
        mapper.update(null, wrapper);
    }

    /**
     * 删除
     */
    public void remove(Long id) {
        SysApiLog po = new SysApiLog();
        Date now = DateUtil.date();
        po.setId(id);
        mapper.updateById(po);
    }

    /**
     * 按主键查询
     */
    public SysApiLog queryById(Long id) {
        LambdaQueryWrapper<SysApiLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysApiLog::getId, id);
        wrapper.last("limit 1");
        return mapper.selectOne(wrapper);
    }


    /**
     * 按条件查询
     */
    public List<SysApiLog> queryList(SysApiLogQuery query) {
        LambdaQueryWrapper<SysApiLog> wrapper = Wrappers.lambdaQuery();
        return mapper.selectList(wrapper);
    }


}

