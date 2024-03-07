package com.bruce.phoenix.auth.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

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
@TableName("auth_role")
@ApiModel(value = "Role对象", description = "角色")
public class Role implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "角色编码")
    private String code;

    @ApiModelProperty(value = "角色备注")
    private String note;

    @ApiModelProperty(value = "是否启用")
    private String isEnable;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除")
    private String isDelete;

}
