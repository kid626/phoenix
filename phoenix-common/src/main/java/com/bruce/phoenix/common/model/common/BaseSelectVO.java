package com.bruce.phoenix.common.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc 通用下拉框
 * @ProjectName phoenix
 * @Date 2024/8/21 9:29
 * @Author Bruce
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseSelectVO {

    private String code;
    private String name;

}
