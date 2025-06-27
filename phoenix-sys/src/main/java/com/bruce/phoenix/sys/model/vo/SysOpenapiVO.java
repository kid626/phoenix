package com.bruce.phoenix.sys.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Copyright Copyright © 2025 Bruce . All rights reserved.
 * @Desc 返回实体类
 * @ProjectName phoenix-sys
 * @Date 2025-06-27
 * @Author Bruce
*/
@Data
@ApiModel(value = "SysOpenapiVO对象", description = "")
public class SysOpenapiVO implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "accessKey")
    private String accessKey;

    @ApiModelProperty(value = "accessSecret")
    private String accessSecret;

    @ApiModelProperty(value = "备注")
    private String note;

}
