package com.bruce.phoenix.sys.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/2/8 11:01
 * @Author Bruce
 */
@Data
public class SysLogVO {


    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "操作模块")
    private String module;

    @ApiModelProperty(value = "业务类型")
    private String businessType;

    @ApiModelProperty(value = "业务类型")
    private String businessTypeStr;

    @ApiModelProperty(value = "请求方法")
    private String method;

    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "操作类别")
    private String operatorType;

    @ApiModelProperty(value = "操作类别")
    private String operatorTypeStr;

    @ApiModelProperty(value = "请求URL")
    private String operUrl;

    @ApiModelProperty(value = "主机地址")
    private String operIp;

    @ApiModelProperty(value = "请求参数")
    private String operParam;

    @ApiModelProperty(value = "返回参数")
    private String operResult;

    @ApiModelProperty(value = "操作状态")
    private String status;

    @ApiModelProperty(value = "操作状态")
    private String statusStr;

    @ApiModelProperty(value = "错误消息")
    private String errorMsg;

    @ApiModelProperty(value = "请求用户")
    private String createUser;

    @ApiModelProperty(value = "请求时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
