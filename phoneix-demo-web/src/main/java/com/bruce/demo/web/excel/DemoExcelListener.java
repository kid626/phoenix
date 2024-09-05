package com.bruce.demo.web.excel;

import com.bruce.demo.web.service.DemoUserService;
import com.bruce.phoenix.core.excel.listener.SimpleAnalysisEventListener;
import com.bruce.phoenix.core.excel.service.IExcelService;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/5 15:32
 * @Author Bruce
 */
public class DemoExcelListener extends SimpleAnalysisEventListener<DemoExcelModel> {

    private DemoUserService demoUserService;

    public DemoExcelListener(DemoUserService demoUserService) {
        this.demoUserService = demoUserService;
    }

    @Override
    public IExcelService<DemoExcelModel> getExcelService() {
        return new DemoExcelServiceImpl(demoUserService);
    }

}
