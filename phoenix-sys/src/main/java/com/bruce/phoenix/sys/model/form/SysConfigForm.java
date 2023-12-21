package com.bruce.phoenix.sys.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc
 * @ProjectName demo
 * @Date 2022/6/30 14:38
 * @Author fzh
 */
@Data
@ApiModel("系统配置表单")
public class SysConfigForm {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "配置唯一编码")
    @NotBlank
    private String configCode;

    @ApiModelProperty(value = "配置名称")
    @NotBlank
    private String configName;

    @ApiModelProperty(value = "配置内容")
    @NotBlank
    private String configValue;

}
