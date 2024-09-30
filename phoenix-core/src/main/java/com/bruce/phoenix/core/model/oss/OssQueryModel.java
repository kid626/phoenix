package com.bruce.phoenix.core.model.oss;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/29 16:04
 * @Author Bruce
 */
@Data
@ApiModel("文件查询模型")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OssQueryModel {

    @ApiModelProperty("文件短路径 二选一")
    private String filePath;
    @JSONField(serializeUsing = ToStringSerializer.class)
    @ApiModelProperty("文件主键 二选一")
    private Long fileId;
    @ApiModelProperty("有效时间,单位秒,默认7天")
    private Integer expires;

}
