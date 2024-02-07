package com.bruce.phoenix.sys.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2023/12/20 13:44
 * @Author Bruce
 */
@Data
@ApiModel("数据字段新增表单")
public class SysDictForm {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "父主键,第一级传 0")
    @NotNull
    private Long pId;

    @ApiModelProperty(value = "字典编码,第一级传 0")
    @NotBlank
    private String dictCode;

    @ApiModelProperty(value = "父编码")
    @NotBlank
    private String pCode;

    @ApiModelProperty(value = "字典名称")
    @NotBlank
    private String dictName;

    @ApiModelProperty(value = "字典值")
    @NotBlank
    private String dictValue;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;

    @ApiModelProperty(value = "是否启用")
    private String isEnable;

}
