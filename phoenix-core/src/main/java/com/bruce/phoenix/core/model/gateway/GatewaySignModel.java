package com.bruce.phoenix.core.model.gateway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/2/28 18:44
 * @Author Bruce
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GatewaySignModel implements Serializable {

    /**
     * 你要请求的路径（根据你的网关配置自行修改）/API/服务/ABC
     */
    private String path;
    /**
     * 网关分配的 appSecret
     */
    private String appSecret;
    /**
     * 网关分配的 appKey
     */
    private String appKey;
    /**
     * 网关地址
     */
    private String host;
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 版本号，默认为1.0.0
     */
    private String version;

}
