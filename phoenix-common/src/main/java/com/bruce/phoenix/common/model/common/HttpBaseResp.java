package com.bruce.phoenix.common.model.common;

import lombok.Data;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/8/4 10:31
 * @Author Bruce
 */
@Data
public class HttpBaseResp implements IHttpBaseResp {

    private String code;
    private String errCode;
    private String message;
    private String requestId;
    private String data;

    @Override
    public boolean isSuccess() {
        return "200".equals(code);
    }

    @Override
    public String getData() {
        return this.data;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
