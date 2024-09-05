package com.bruce.phoenix.core.excel.listener;

import com.alibaba.excel.event.AnalysisEventListener;
import com.bruce.phoenix.core.excel.model.BaseImportModel;
import com.bruce.phoenix.core.excel.service.IExcelService;

import java.util.List;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc    分析模板
 * @ProjectName phoenix
 * @Date 2024/9/4 14:58
 * @Author Bruce
 */
public abstract class AbstractAnalysisEventListener<T extends BaseImportModel> extends AnalysisEventListener<T> {

    /**
     * 获取所有数据列表
     */
    public abstract List<T> getAllDataList();

    /**
     * 获取失败数据列表
     */
    public abstract List<T> getFailureDataList();

    /**
     * 获取成功数据列表
     */
    public abstract List<T> getSuccessDataList();

    public abstract IExcelService<T> getExcelService();
}
