package com.bruce.phoenix.auth.model.form;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* @Copyright Copyright © 2024 Bruce . All rights reserved.
* @Desc 用户-组织关系表单实体类
* @ProjectName phoenix-auth
* @Date 2024-03-07
* @Author Bruce
*/
@Data
@ApiModel(value = "UserOrgForm对象", description = "用户-组织关系")
public class UserOrgForm implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户主键")
    private Long userId;

    @ApiModelProperty(value = "组织编码")
    private String orgCode;

    @ApiModelProperty(value = "排序")
    private Integer sortOrder;

}
