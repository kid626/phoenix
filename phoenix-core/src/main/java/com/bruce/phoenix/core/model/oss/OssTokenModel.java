package com.bruce.phoenix.core.model.oss;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/30 8:55
 * @Author Bruce
 */
@ApiModel("获取token表单")
@Data
public class OssTokenModel {

    @ApiModelProperty("桶名称")
    private String bucketName;
    @ApiModelProperty("是否公有桶")
    private boolean isPublic;

}
