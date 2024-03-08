package com.bruce.phoenix.sys.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 系统第三方请求日志表单实体类
 * @ProjectName phoenix-sys
 * @Date 2024-03-01
 * @Author Bruce
 */
@Data
@ApiModel(value = "SysApiLogForm对象", description = "系统第三方请求日志")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysApiLogForm implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "请求唯一id")
    private String requestId;

    @ApiModelProperty(value = "请求 url")
    private String requestUrl;

    @ApiModelProperty(value = "请求头")
    private String requestHeaders;

    @ApiModelProperty(value = "请求方法")
    private String requestMethod;

    @ApiModelProperty(value = "请求表单")
    private String requestForm;

    @ApiModelProperty(value = "返回体")
    private String responseBody;

    @ApiModelProperty(value = "返回http code")
    private String responseCode;

    @ApiModelProperty(value = "请求时间")
    private Date requestTime;

    @ApiModelProperty(value = "响应时间")
    private Date responseTime;

}
