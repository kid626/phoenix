package com.bruce.phoenix.core.excel.listener;


import com.alibaba.excel.context.AnalysisContext;
import com.bruce.phoenix.core.excel.model.BaseImportModel;
import com.bruce.phoenix.core.excel.service.IExcelService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/4 15:56
 * @Author Bruce
 */
@Slf4j
public class SimpleAnalysisEventListener<T extends BaseImportModel> extends AbstractAnalysisEventListener<T> {

    private List<T> allDataList = new ArrayList<>();
    private List<T> successDataList = new ArrayList<>();
    private List<T> failureDataList = new ArrayList<>();

    @Override
    public List<T> getAllDataList() {
        return allDataList;
    }

    @Override
    public List<T> getFailureDataList() {
        return getExcelService().proceed(allDataList);
    }

    @Override
    public List<T> getSuccessDataList() {
        return successDataList;
    }

    @Override
    public IExcelService<T> getExcelService() {
        return (t) -> t;
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        allDataList.add(t);
        successDataList.add(t);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("数据分析完成,累计数据:{},成功数据:{},失败数据:{}", allDataList.size(), successDataList.size(), failureDataList.size());
    }
}
