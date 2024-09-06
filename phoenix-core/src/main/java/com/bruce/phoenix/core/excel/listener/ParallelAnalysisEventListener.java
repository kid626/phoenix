package com.bruce.phoenix.core.excel.listener;


import cn.hutool.json.JSONUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.bruce.phoenix.core.excel.model.BaseImportModel;
import com.bruce.phoenix.core.excel.service.DefaultExcelService;
import com.bruce.phoenix.core.excel.service.IExcelService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc 并行处理监听器, 边读取, 边处理
 * @ProjectName phoenix
 * @Date 2024/9/4 15:56
 * @Author Bruce
 */
@Slf4j
public class ParallelAnalysisEventListener<T extends BaseImportModel> extends AbstractAnalysisEventListener<T> {

    private final List<T> allDataList = new ArrayList<>();
    private final List<T> successDataList = new ArrayList<>();
    private final List<T> failureDataList = new ArrayList<>();

    @Override
    public List<T> getAllDataList() {
        return allDataList;
    }

    @Override
    public final List<T> getFailureDataList() {
        return failureDataList;
    }

    @Override
    public List<T> getSuccessDataList() {
        return successDataList;
    }

    @Override
    public IExcelService<T> getExcelService() {
        return new DefaultExcelService<>();
    }

    @Override
    public final void invoke(T t, AnalysisContext analysisContext) {
        allDataList.add(t);
        IExcelService<T> excelService = getExcelService();
        T temp = excelService.isValidate(t);
        if (temp != null) {
            failureDataList.add(temp);
        } else {
            successDataList.add(t);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("数据分析完成,累计数据:{},成功数据:{},失败数据:{}", allDataList.size(), successDataList.size(), failureDataList.size());
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("头部信息:{}", JSONUtil.toJsonStr(headMap));
    }
}
