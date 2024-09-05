package com.bruce.phoenix.core.excel.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/4 15:10
 * @Author Bruce
 */
@Data
@ApiModel("导入错误模型")
public class ImportResultModel<T> {

    @ApiModelProperty("错误数据条数")
    private Long errorCount;

    @ApiModelProperty("错误信息标识")
    private String operationId;

    @ApiModelProperty("错误数据列表")
    private List<T> errorDataList;
}
