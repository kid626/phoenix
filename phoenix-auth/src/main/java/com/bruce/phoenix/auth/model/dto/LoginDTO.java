package com.bruce.phoenix.auth.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName security
 * @Date 2021/12/23 9:40
 * @Author fzh
 */
@Data
public class LoginDTO {

    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("rid")
    private String rid;
    @ApiModelProperty("验证码code")
    private String code;

}
