package com.bruce.phoenix.core.excel.service;

import com.bruce.phoenix.core.excel.model.BaseImportModel;

import java.util.List;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/4 16:08
 * @Author Bruce
 */
public interface IExcelService<T extends BaseImportModel> {

    /**
     * 列表转换 适合批量处理的场景
     */
    default List<T> proceed(List<T> list) {
        return list;
    }

    /**
     * 单个转换 适合边读取边处理的场景
     *
     * @return 为空说明校验通过
     */
    default T isValidate(T t) {
        return t;
    }

}
