package com.bruce.phoenix.sys.model.query;

import com.bruce.phoenix.common.model.common.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 系统第三方请求日志查询实体类
 * @ProjectName phoenix-sys
 * @Date 2024-03-01
 * @Author Bruce
 */
@Data
@ApiModel(value = "SysApiLogQuery对象", description = "系统第三方请求日志")
public class SysApiLogQuery extends BasePageQuery implements Serializable {


}
