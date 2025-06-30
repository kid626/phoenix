package com.bruce.phoenix.sys.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* @Copyright Copyright © 2025 Bruce . All rights reserved.
* @Desc 表单实体类
* @ProjectName phoenix-sys
* @Date 2025-06-27
* @Author Bruce
*/
@Data
@ApiModel(value = "SysOpenapiForm对象", description = "")
public class SysOpenapiForm implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "备注")
    private String note;

}
