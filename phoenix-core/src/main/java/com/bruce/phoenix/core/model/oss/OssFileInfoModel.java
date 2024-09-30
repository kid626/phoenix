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
@ApiModel("文件模型")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OssFileInfoModel {

    @ApiModelProperty("桶名")
    private String bucketName;
    @ApiModelProperty("是否公有桶，默认不是")
    private boolean isPublic = false;
    @ApiModelProperty("文件短路径")
    private String filePath;
    @ApiModelProperty("文件完整路径")
    private String fileUrl;
    @ApiModelProperty("文件base64,服务间不通过文件流传递")
    private String fileBase64;
    @ApiModelProperty("文件名称")
    private String fileName;
    @JSONField(serializeUsing = ToStringSerializer.class)
    @ApiModelProperty("文件主键")
    private Long fileId;

}
