package com.bruce.demo.web.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Copyright Copyright © 2023 Bruce . All rights reserved.
 * @Desc 实体类
 * @ProjectName phoenix-demo
 * @Date 2023-12-18
 * @Author Bruce
 */
@Data
@TableName("demo_user")
@ApiModel(value = "DemoUser对象", description = "")
public class DemoUser implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "")
    private Long id;

    @ApiModelProperty(value = "")
    private Long deptId;

    @ApiModelProperty(value = "")
    private String name;

    @ApiModelProperty(value = "")
    private Integer age;

    @ApiModelProperty(value = "")
    private Integer grade;

    @ApiModelProperty(value = "")
    private String email;

    @ApiModelProperty(value = "")
    private String dateColumn;

    @ApiModelProperty(value = "")
    private String tenantCode;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除")
    private String isDelete;

}
