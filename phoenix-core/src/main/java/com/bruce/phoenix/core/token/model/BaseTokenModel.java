package com.bruce.phoenix.core.token.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc    通用token模型
 * @ProjectName phoenix
 * @Date 2024/9/13 15:00
 * @Author Bruce
 */
@Data
@ApiModel("通用token模型")
public class BaseTokenModel {

    @ApiModelProperty("token值")
    private String token;
    @ApiModelProperty("过期时间 时间戳,毫秒")
    private Long expireTime;
}
