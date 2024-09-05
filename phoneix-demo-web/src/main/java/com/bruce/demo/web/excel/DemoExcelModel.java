package com.bruce.demo.web.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.bruce.phoenix.core.excel.model.BaseImportModel;
import lombok.Data;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/5 15:31
 * @Author Bruce
 */
@Data
public class DemoExcelModel extends BaseImportModel {

    @ExcelProperty(index = 0)
    private String param1;

    @ExcelProperty(index = 1)
    private String param2;

    @ExcelProperty(index = 2)
    private String param3;

    @ExcelProperty(index = 3)
    private String param4;

}
