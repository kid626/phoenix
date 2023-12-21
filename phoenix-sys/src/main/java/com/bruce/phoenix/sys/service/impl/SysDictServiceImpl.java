package com.bruce.phoenix.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bruce.phoenix.common.model.enums.YesOrNoEnum;
import com.bruce.phoenix.common.tree.TreeNodeCacheService;
import com.bruce.phoenix.common.tree.TreeNodeConvertService;
import com.bruce.phoenix.sys.mapper.SysDictMapper;
import com.bruce.phoenix.sys.model.converter.SysDictConverter;
import com.bruce.phoenix.sys.model.po.SysDict;
import com.bruce.phoenix.sys.model.tree.dict.SysDictForm;
import com.bruce.phoenix.sys.model.tree.dict.SysDictTree;
import com.bruce.phoenix.sys.model.tree.dict.SysDictVO;
import com.bruce.phoenix.sys.service.SysDictService;
import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Copyright Copyright © 2022 Bruce . All rights reserved.
 * @Desc 系统数据字典service 实现类
 * @ProjectName demo
 * @Date 2022-6-20 9:50:39
 * @Author Bruce
 */
@Service
public class SysDictServiceImpl extends TreeNodeCacheService<SysDictTree, SysDictForm, SysDictVO> implements SysDictService {

    @Resource
    private SysDictMapper mapper;
    @Resource
    private IdGenerator idGenerator;

    private static final SysDictConverter CONVERTER = new SysDictConverter();


    @Override
    public SysDict queryById(Long id) {
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDict::getId, id).eq(SysDict::getIsDeleted, YesOrNoEnum.NO.getCode());
        wrapper.last("limit 1");
        return mapper.selectOne(wrapper);
    }

    @Override
    public SysDict queryByCode(String code) {
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDict::getDictCode, code).eq(SysDict::getIsDeleted, YesOrNoEnum.NO.getCode());
        wrapper.last("limit 1");
        return mapper.selectOne(wrapper);
    }

    @Override
    public List<SysDict> queryByPid(Long pId) {
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDict::getPId, pId).eq(SysDict::getIsDeleted, YesOrNoEnum.NO.getCode());
        return mapper.selectList(wrapper);
    }

    @Override
    public TreeNodeConvertService<SysDictTree, SysDictForm, SysDictVO> generateConvert() {
        return new SysDictConvertServiceImpl();
    }

    @Override
    public SysDictTree queryTreeById(Long id) {
        SysDict sysDict = queryById(id);
        if (sysDict == null) {
            return null;
        }
        return SysDictConverter.convert2Tree(sysDict);
    }

    @Override
    public SysDictTree queryTreeByCode(String code) {
        SysDict sysDict = queryByCode(code);
        if (sysDict == null) {
            return null;
        }
        return SysDictConverter.convert2Tree(sysDict);
    }

    @Override
    public List<SysDictTree> queryTreeByPid(Long pId) {
        List<SysDict> list = queryByPid(pId);
        return list.stream().map(SysDictConverter::convert2Tree).collect(Collectors.toList());
    }

    @Override
    public List<SysDictTree> getAll() {
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDict::getIsDeleted, YesOrNoEnum.NO.getCode());
        wrapper.eq(SysDict::getIsEnable, YesOrNoEnum.YES.getCode());
        List<SysDict> list = mapper.selectList(wrapper);
        return list.stream().map(SysDictConverter::convert2Tree).collect(Collectors.toList());
    }

    @Override
    public List<SysDictTree> queryLikeCode(String code) {
        LambdaQueryWrapper<SysDict> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(code)) {
            wrapper.likeRight(SysDict::getDictCode, code);
        }
        wrapper.eq(SysDict::getIsDeleted, YesOrNoEnum.NO.getCode());
        List<SysDict> list = mapper.selectList(wrapper);
        return list.stream().map(SysDictConverter::convert2Tree).collect(Collectors.toList());
    }

    @Override
    public long insert(SysDictTree tree) {
        SysDict po = SysDictConverter.convert2Po(tree);
        Long id = po.getId();
        if (id == null) {
            id = idGenerator.generateId().longValue();
        }
        po.setId(id);
        if (StrUtil.isBlank(po.getIsEnable())) {
            po.setIsEnable(YesOrNoEnum.YES.getCode());
        }
        po.setIsDefault(YesOrNoEnum.NO.getCode());
        po.setIsDeleted(YesOrNoEnum.NO.getCode());
        po.setCreateTime(DateUtil.date());
        mapper.insert(po);
        return id;
    }

    @Override
    public long updateEntity(SysDictTree tree) {
        SysDict po = SysDictConverter.convert2Po(tree);
        po.setUpdateTime(DateUtil.date());
        return mapper.updateById(po);
    }

}
