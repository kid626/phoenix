package com.bruce.phoenix.core.config.mybatisplus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/5/29 14:39
 * @Author Bruce
 */
public interface CustomBaseMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入，通过 {@link BatchSaveSqlInjector 注入实现}
     */
    int batchSave(@Param("list") List<T> list);

}
