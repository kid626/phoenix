package com.bruce.phoenix.oss.dao;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bruce.phoenix.common.model.enums.YesOrNoEnum;
import com.bruce.phoenix.oss.mapper.FileInfoMapper;
import com.bruce.phoenix.oss.model.po.FileInfo;
import com.bruce.phoenix.oss.model.query.FileInfoQuery;
import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc Dao层
 * @ProjectName phoenix-oss
 * @Date 2024-09-29
 * @Author Bruce
 */
@Repository
@Slf4j
public class FileInfoDao {

    @Resource
    private FileInfoMapper mapper;
    @Resource
    private IdGenerator idGenerator;

    /**
     * 新增
     */
    public Long save(FileInfo po) {
        long id = idGenerator.generateId().longValue();
        Date now = DateUtil.date();
        po.setId(id);
        po.setCreateTime(now);
        po.setUpdateTime(now);
        po.setIsDelete(YesOrNoEnum.NO.getCode());
        mapper.insert(po);
        return po.getId();
    }

    /**
     * 更新
     */
    public void update(FileInfo po) {
        Date now = DateUtil.date();
        po.setUpdateTime(now);
        mapper.updateById(po);
    }

    /**
     * 删除
     */
    public void remove(Long id) {
        FileInfo po = new FileInfo();
        Date now = DateUtil.date();
        po.setId(id);
        po.setUpdateTime(now);
        po.setIsDelete(YesOrNoEnum.YES.getCode());
        mapper.updateById(po);
    }

    /**
     * 按主键查询
     */
    public FileInfo queryById(Long id) {
        LambdaQueryWrapper<FileInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(FileInfo::getIsDelete, YesOrNoEnum.NO.getCode());
        wrapper.eq(FileInfo::getId, id);
        wrapper.last("limit 1");
        return mapper.selectOne(wrapper);
    }


    /**
     * 按条件查询
     */
    public List<FileInfo> queryList(FileInfoQuery query) {
        LambdaQueryWrapper<FileInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(FileInfo::getIsDelete, YesOrNoEnum.NO.getCode());
        // TODO 其他查询条件
        return mapper.selectList(wrapper);
    }

    public FileInfo queryByPath(String filePath) {
        LambdaQueryWrapper<FileInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(FileInfo::getIsDelete, YesOrNoEnum.NO.getCode());
        wrapper.eq(FileInfo::getPath, filePath);
        wrapper.last("limit 1");
        return mapper.selectOne(wrapper);
    }
}

