package com.bruce.phoenix.common.model.screen;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/13 14:17
 * @Author Bruce
 */
@Data
@ApiModel("大屏基础数据模型")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScreenBaseDataVO<T> implements Serializable {

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("数据集合")
    private List<T> data;

}
