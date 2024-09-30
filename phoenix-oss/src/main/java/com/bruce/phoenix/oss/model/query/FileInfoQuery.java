package com.bruce.phoenix.oss.model.query;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 查询实体类
 * @ProjectName phoenix-oss
 * @Date 2024-09-29
 * @Author Bruce
 */
@Data
@ApiModel(value = "FileInfoQuery对象", description = "")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoQuery implements Serializable {


    @ApiModelProperty(value = "是否公有桶，默认不是", hidden = true)
    private boolean isPublic = false;
    @ApiModelProperty(value = "桶名", hidden = true)
    private String bucketName;
    @ApiModelProperty("文件短路径 二选一")
    private String filePath;
    @JSONField(serializeUsing = ToStringSerializer.class)
    @ApiModelProperty("文件主键 二选一")
    private Long fileId;
    @ApiModelProperty("有效时间,单位秒,默认7天")
    private Integer expires;


}
