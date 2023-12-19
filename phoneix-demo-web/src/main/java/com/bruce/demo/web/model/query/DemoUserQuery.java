package com.bruce.demo.web.model.query;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.bruce.phoenix.common.model.common.BasePageQuery;

/**
 * @Copyright Copyright © 2023 Bruce . All rights reserved.
 * @Desc 查询实体类
 * @ProjectName phoenix-demo
 * @Date 2023-12-19
 * @Author Bruce
 */
@Data
@ApiModel(value = "DemoUserQuery对象", description = "")
public class DemoUserQuery extends BasePageQuery implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    private Long id;


    @ApiModelProperty(value = "部门id")
    private Long deptId;


    @ApiModelProperty(value = "名称")
    private String name;


    @ApiModelProperty(value = "年龄")
    private Integer age;


    @ApiModelProperty(value = "性别")
    private Integer grade;


    @ApiModelProperty(value = "邮箱")
    private String email;


    @ApiModelProperty(value = "序列")
    private String dateColumn;


    @ApiModelProperty(value = "租户号")
    private String tenantCode;


    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


    @ApiModelProperty(value = "是否删除")
    private String isDelete;


}
