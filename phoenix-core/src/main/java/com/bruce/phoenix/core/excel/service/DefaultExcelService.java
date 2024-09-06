package com.bruce.phoenix.core.excel.service;

import com.bruce.phoenix.core.excel.model.BaseImportModel;

import java.util.List;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/6 8:43
 * @Author Bruce
 */
public class DefaultExcelService<T extends BaseImportModel> implements IExcelService<T> {

    @Override
    public List<T> proceed(List<T> list) {
        return list;
    }

    @Override
    public T isValidate(T t) {
        return t;
    }
}
