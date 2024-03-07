package com.bruce.phoenix.auth.model.form;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @Copyright Copyright © 2024 Bruce . All rights reserved.
* @Desc 表单实体类
* @ProjectName phoenix-auth
* @Date 2024-03-05
* @Author Bruce
*/
@Data
@ApiModel(value = "RoleForm对象", description = "")
public class RoleForm implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "")
    private Long id;

    @ApiModelProperty(value = "")
    private String name;

    @ApiModelProperty(value = "")
    private String code;

}
