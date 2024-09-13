package com.bruce.phoenix.common.model.screen;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/13 14:27
 * @Author Bruce
 */
@Data
@ApiModel("大屏name value模型")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScreenNameValueVO<T> {

    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("值")
    private T value;
}
