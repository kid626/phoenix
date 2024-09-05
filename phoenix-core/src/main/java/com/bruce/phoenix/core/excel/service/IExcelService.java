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
@FunctionalInterface
public interface IExcelService<T extends BaseImportModel> {


    List<T> proceed(List<T> list);

}
