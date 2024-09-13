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
 * @Desc 柱状图
 * @ProjectName phoenix
 * @Date 2024/9/13 14:14
 * @Author Bruce
 */
@Data
@ApiModel("柱状图实体")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScreenBarChartVO<T> implements Serializable {

    @ApiModelProperty("横坐标")
    private List<String> xAxis;

    @ApiModelProperty("纵坐标")
    private List<List<ScreenBaseDataVO<T>>> series;

}
