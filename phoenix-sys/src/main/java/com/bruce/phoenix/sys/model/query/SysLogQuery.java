package com.bruce.phoenix.sys.model.query;

import com.bruce.phoenix.common.annotation.EnumValid;
import com.bruce.phoenix.common.model.common.BasePageQuery;
import com.bruce.phoenix.common.model.enums.BusinessStatusEnum;
import com.bruce.phoenix.common.model.enums.BusinessTypeEnum;
import com.bruce.phoenix.common.model.enums.OperatorTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/2/8 11:03
 * @Author Bruce
 */
@Data
@ApiModel("系统日志查询实体")
public class SysLogQuery extends BasePageQuery {

    @ApiModelProperty(value = "业务类型")
    @EnumValid(target = BusinessTypeEnum.class)
    private String businessType;

    @ApiModelProperty(value = "请求方法")
    private String method;

    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "操作类别")
    @EnumValid(target = OperatorTypeEnum.class)
    private String operatorType;

    @ApiModelProperty(value = "操作状态")
    @EnumValid(target = BusinessStatusEnum.class)
    private String status;

}
