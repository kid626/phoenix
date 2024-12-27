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
 * @Desc 饼图
 * @ProjectName phoenix
 * @Date 2024/9/13 14:14
 * @Author Bruce
 */
@Data
@ApiModel("饼图实体")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScreenPieChartVO<T> implements Serializable {

    @ApiModelProperty("数据")
    private List<ScreenBaseDataVO<ScreenNameValueVO<T>>> series;

}
