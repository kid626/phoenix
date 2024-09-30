package com.bruce.phoenix.oss.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/29 15:46
 * @Author Bruce
 */
@ConfigurationProperties(prefix = "minio")
@Configuration
@ApiModel("minio配置")
@Data
public class MinioProperties {

    @ApiModelProperty("默认桶名")
    private String bucketName;
    @ApiModelProperty("endpoint")
    private String endpoint;
    @ApiModelProperty("外部域名")
    private String domain;
    @ApiModelProperty("accessKey")
    private String accessKey;
    @ApiModelProperty("secretKey")
    private String secretKey;

}
