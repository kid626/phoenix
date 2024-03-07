package com.bruce.phoenix.auth.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @Copyright Copyright © 2021 Bruce . All rights reserved.
 * @Desc 实体类
 * @ProjectName security
 * @Date 2021-12-22 19:33:26
 * @Author Bruce
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("auth_user_role")
@ApiModel(value = "UserRole对象", description = "用户-角色关系")
public class UserRole implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户主键")
    private Long userId;

    @ApiModelProperty(value = "角色主键")
    private Long roleId;


}
