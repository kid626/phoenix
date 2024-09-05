package com.bruce.phoenix.core.excel.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/4 16:05
 * @Author Bruce
 */
@Data
@ApiModel
public class BaseImportModel {

    @ApiModelProperty("错误原因")
    private String error;
}
