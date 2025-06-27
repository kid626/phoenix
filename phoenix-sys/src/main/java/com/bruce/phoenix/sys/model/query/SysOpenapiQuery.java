package com.bruce.phoenix.sys.model.query;

import com.bruce.phoenix.common.model.common.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Copyright Copyright © 2025 Bruce . All rights reserved.
 * @Desc 查询实体类
 * @ProjectName phoenix-sys
 * @Date 2025-06-27
 * @Author Bruce
 */
@Data
@ApiModel(value = "SysOpenapiQuery对象", description = "")
public class SysOpenapiQuery extends BasePageQuery implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    private Long id;


    @ApiModelProperty(value = "appKey")
    private String appKey;


    @ApiModelProperty(value = "appSecret")
    private String appSecret;


    @ApiModelProperty(value = "备注")
    private String note;


}
