package com.bruce.phoenix.oss.model.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 返回实体类
 * @ProjectName phoenix-oss
 * @Date 2024-09-29
 * @Author Bruce
*/
@Data
@ApiModel(value = "FileInfoVO对象", description = "")
public class FileInfoVO implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "文件大小")
    private Long fileSize;

    @ApiModelProperty(value = "文件url")
    private String fileUrl;

    @ApiModelProperty(value = "md5唯一标识")
    private String identifier;

    @ApiModelProperty(value = "文件来源")
    private String origin;

    @ApiModelProperty(value = "文件路径")
    private String path;

    @ApiModelProperty(value = "文件基础路径")
    private String basePath;

    @ApiModelProperty(value = "扩展名")
    private String extendName;

    @ApiModelProperty(value = "原始文件名")
    private String originFileName;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件类型（图片，文档，视频，音频，其他）")
    private String fileTypeCode;

    @ApiModelProperty(value = "文件类型名称")
    private String fileTypeName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "是否删除")
    private String isDelete;

}
