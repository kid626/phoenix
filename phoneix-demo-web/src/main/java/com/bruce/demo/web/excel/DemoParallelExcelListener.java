package com.bruce.demo.web.excel;

import com.bruce.demo.web.service.DemoUserService;
import com.bruce.phoenix.core.excel.listener.ParallelAnalysisEventListener;
import com.bruce.phoenix.core.excel.service.IExcelService;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/5 15:32
 * @Author Bruce
 */
public class DemoParallelExcelListener extends ParallelAnalysisEventListener<DemoExcelModel> {

    private DemoUserService demoUserService;

    public DemoParallelExcelListener(DemoUserService demoUserService) {
        this.demoUserService = demoUserService;
    }

    @Override
    public IExcelService<DemoExcelModel> getExcelService() {
        return new DemoExcelServiceImpl(demoUserService);
    }

}
