package com.bruce.phoenix.auth.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Date;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 实体类
 * @ProjectName phoenix-auth
 * @Date 2024-03-05
 * @Author Bruce
 */
@Data
@TableName("resource")
@ApiModel(value = "Resource对象", description = "")
public class Resource implements Serializable, GrantedAuthority {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "")
    private Long id;

    @ApiModelProperty(value = "资源编码")
    private String code;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "父编码")
    private String parentCode;

    @ApiModelProperty(value = "请求方法(0:全匹配,1:POST,2:DELETE,3:PUT,4:GET)")
    private Integer method;

    @ApiModelProperty(value = "资源路径")
    private String url;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    @ApiModelProperty(value = "类型标记(0:菜单,1:按钮)")
    private Integer type;

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

    @Override
    public String getAuthority() {
        return this.code;
    }
}
