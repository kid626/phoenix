package com.bruce.phoenix.auth.model.query;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.bruce.phoenix.common.model.common.BasePageQuery;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 组织查询实体类
 * @ProjectName phoenix-auth
 * @Date 2024-03-07
 * @Author Bruce
 */
@Data
@ApiModel(value = "OrgQuery对象", description = "组织")
public class OrgQuery extends BasePageQuery implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    private Long id;


    @ApiModelProperty(value = "组织名称")
    private String name;


    @ApiModelProperty(value = "组织编号")
    private String code;


    @ApiModelProperty(value = "上级组织id")
    private Long parentId;


    @ApiModelProperty(value = "上级组织编码")
    private String parentCode;


    @ApiModelProperty(value = "组织排序码")
    private Integer sortOrder;


    @ApiModelProperty(value = "备注")
    private String note;


    @ApiModelProperty(value = "是否启用")
    private String isEnable;


    @ApiModelProperty(value = "创建人")
    private String createUser;


    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    @ApiModelProperty(value = "修改人")
    private String updateUser;


    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


    @ApiModelProperty(value = "是否删除")
    private String isDelete;


}
