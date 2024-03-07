package com.bruce.phoenix.auth.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/7 13:10
 * @Author Bruce
 */
@Data
@ApiModel("验证码实体")
public class CaptchaDTO {

    @NotBlank
    @ApiModelProperty("唯一rid")
    private String rid;

    @ApiModelProperty("宽度")
    private Integer width = 160;

    @ApiModelProperty("高度")
    private Integer height = 40;

}
