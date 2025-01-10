package com.bruce.phoenix.common.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/1/10 14:32
 * @Author Bruce
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeometryVO implements Serializable {


    /**
     * type : Point
     * coordinates : ["120.379409","30.297462"]
     */

    private String type;
    private List<String> coordinates;
}
