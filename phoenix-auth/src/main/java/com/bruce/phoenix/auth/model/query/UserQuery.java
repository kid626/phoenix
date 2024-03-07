package com.bruce.phoenix.auth.model.query;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.bruce.phoenix.common.model.common.BasePageQuery;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 查询实体类
 * @ProjectName phoenix-auth
 * @Date 2024-03-05
 * @Author Bruce
 */
@Data
@ApiModel(value = "UserQuery对象", description = "")
public class UserQuery extends BasePageQuery implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "")
    private Long id;


    @ApiModelProperty(value = "")
    private String username;


    @ApiModelProperty(value = "盐值")
    private String salt;


    @ApiModelProperty(value = "")
    private String password;


    @ApiModelProperty(value = "是否启用")
    private String enable;


    @ApiModelProperty(value = "")
    private String isDelete;


    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    @ApiModelProperty(value = "创建人")
    private String createUser;


    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


    @ApiModelProperty(value = "更新人")
    private String updateUser;


}
