package com.bruce.phoenix.core.model.gateway;

import lombok.Data;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/2/28 19:54
 * @Author Bruce
 */
@Data
public class GatewayResult {

    private static final int SUCCESS_CODE = 200;

    private int code;
    private String message;
    private Object data;

    public boolean isSuccess() {
        return code == SUCCESS_CODE;
    }

}
