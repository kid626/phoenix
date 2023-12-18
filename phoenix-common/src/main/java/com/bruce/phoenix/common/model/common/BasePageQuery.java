package com.bruce.phoenix.common.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName file-manager-system
 * @Date 2023/4/7 15:15
 * @Author fzh
 */
@Data
public class BasePageQuery implements Serializable {

    @ApiModelProperty(value = "每页返回最大记录数", example = "10")
    @Min(value = 1L, message = "pageSize参数错误")
    @Max(value = 200L, message = "pageSize参数错误")
    @NotNull(message = "pageSize参数错误")
    private Integer pageSize;
    @ApiModelProperty(value = "请求第几页", example = "1")
    @Min(value = 1L, message = "page参数错误")
    @NotNull(message = "page参数错误")
    private Integer pageNum;

}
