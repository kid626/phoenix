package com.bruce.phoenix.sys.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc
 * @ProjectName demo
 * @Date 2022/6/30 14:51
 * @Author fzh
 */
@Data
public class SysConfigVO {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "配置唯一编码")
    private String configCode;

    @ApiModelProperty(value = "配置名称")
    private String configName;

    @ApiModelProperty(value = "配置内容")
    private String configValue;

    @ApiModelProperty(value = "是否系统内置")
    private String isDefault;

    @ApiModelProperty(value = "是否启用")
    private String isEnable;

}
