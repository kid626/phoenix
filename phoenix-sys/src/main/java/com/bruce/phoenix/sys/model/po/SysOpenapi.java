package com.bruce.phoenix.sys.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Copyright Copyright © 2025 Bruce . All rights reserved.
 * @Desc 实体类
 * @ProjectName phoenix-sys
 * @Date 2025-06-27
 * @Author Bruce
 */
@Data
@TableName("sys_openapi")
@ApiModel(value = "SysOpenapi对象", description = "")
public class SysOpenapi implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "accessKey")
    private String accessKey;

    @ApiModelProperty(value = "accessSecret")
    private String accessSecret;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "创建用户")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新用户")
    private String updateUser;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除")
    private String isDelete;

}
