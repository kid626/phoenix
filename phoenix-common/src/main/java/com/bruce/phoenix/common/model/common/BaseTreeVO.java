package com.bruce.phoenix.common.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/12/12 11:23
 * @Author Bruce
 */
@Data
@ApiModel("通用树结构实体")
public class BaseTreeVO<T> {

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("父编码")
    private String pCode;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("是否有子节点")
    private boolean hasChildren;

    @ApiModelProperty("额外信息")
    private T ext;

    @ApiModelProperty("子节点")
    private List<BaseTreeVO<T>> child;

}
