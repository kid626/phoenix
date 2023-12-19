package com.bruce.phoenix.common.exception;

import com.bruce.phoenix.common.model.common.Err;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName common-component
 * @Date 2023/4/14 16:38
 * @Author fzh
 */
public class CommonException extends RuntimeException {

    private int code = Err.CUSTOM_ERROR.getCode();

    private Object data;

    /**
     * @param message the detail message. The detail message is saved for later retrieval by the
     *                {@link #getMessage()} method.
     */
    public CommonException(String message) {
        super(message);
    }

    public CommonException(Err err) {
        super(err.getMessage());
        this.code = err.getCode();
    }

    public CommonException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CommonException(int code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
