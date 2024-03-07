package com.bruce.phoenix.auth.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/7 10:32
 * @Author Bruce
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDTO implements Serializable {

    @ApiModelProperty(value = "资源编码")
    private String code;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "父编码")
    private String parentCode;

    @ApiModelProperty(value = "请求方法(0:全匹配,1:POST,2:DELETE,3:PUT,4:GET)")
    private Integer method;

    @ApiModelProperty(value = "资源路径")
    private String url;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    @ApiModelProperty(value = "类型标记(0:菜单,1:按钮)")
    private Integer type;

    @ApiModelProperty(value = "版本号")
    private String version;

}
