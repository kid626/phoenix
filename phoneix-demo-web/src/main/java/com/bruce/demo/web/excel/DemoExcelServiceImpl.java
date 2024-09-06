package com.bruce.demo.web.excel;

import cn.hutool.core.util.StrUtil;
import com.bruce.demo.web.service.DemoUserService;
import com.bruce.phoenix.core.excel.service.IExcelService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/5 15:35
 * @Author Bruce
 */
public class DemoExcelServiceImpl implements IExcelService<DemoExcelModel> {

    private DemoUserService demoUserService;

    public DemoExcelServiceImpl(DemoUserService demoUserService) {
        this.demoUserService = demoUserService;
    }


    @Override
    public List<DemoExcelModel> proceed(List<DemoExcelModel> list) {
        List<DemoExcelModel> result = new ArrayList<>();
        for (DemoExcelModel demoExcelModel : list) {

            if (StrUtil.isBlank(demoExcelModel.getParam1())) {
                demoExcelModel.setError("参数1不能为空");
                result.add(demoExcelModel);
                continue;
            }

            if (StrUtil.isBlank(demoExcelModel.getParam2())) {
                demoExcelModel.setError("参数2不能为空");
                result.add(demoExcelModel);
                continue;
            }

            if (StrUtil.isBlank(demoExcelModel.getParam3())) {
                demoExcelModel.setError("参数3不能为空");
                result.add(demoExcelModel);
                continue;
            }
            // 执行新增
            // demoUserService.save();
        }

        return result;
    }

    @Override
    public DemoExcelModel isValidate(DemoExcelModel demoExcelModel) {
        if (StrUtil.isBlank(demoExcelModel.getParam1())) {
            demoExcelModel.setError("参数1不能为空");
            return demoExcelModel;
        }

        if (StrUtil.isBlank(demoExcelModel.getParam2())) {
            demoExcelModel.setError("参数2不能为空");
            return demoExcelModel;
        }

        if (StrUtil.isBlank(demoExcelModel.getParam3())) {
            demoExcelModel.setError("参数3不能为空");
            return demoExcelModel;
        }
        // 执行新增
        // demoUserService.save();

        // 如果校验通过，返回 nul
        return null;
    }
}
