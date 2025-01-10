package com.bruce.phoenix.common.model.common;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/1/10 14:31
 * @Author Bruce
 */
@Data
@ApiModel("地理位置实体")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeoJsonVO<T> implements Serializable {

    /**
     * type : Feature
     * properties : {"bh":"鹰眼1","lx":"监控","cj":"海康","lng":"120.379409","lat":"30.297462","gc":"8","id":200092183,"zt":1}
     * geometry : {"type":"Point","coordinates":["120.379409","30.297462"]}
     */

    private String type = "Feature";
    private T properties;
    private GeometryVO geometry;


}
