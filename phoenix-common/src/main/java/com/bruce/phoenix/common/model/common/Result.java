package com.bruce.phoenix.common.model.common;

import com.bruce.phoenix.common.util.TraceUtil;
import com.unicom.middleware.unicom.common.exception.ServiceException;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName security
 * @Date 2021/12/26 11:39
 * @Author fzh
 */
@Data
public class Result<T> implements Serializable {

    private static int SUCCESS_CODE = 200;
    private String requestId;
    private String msg;
    private long time;
    private int code;
    private T data;

    public Result(int code, String msg, T data) {
        this.time = Instant.now().getEpochSecond();
        this.requestId = TraceUtil.get();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result<String> fail(Err err) {
        return new Result<>(err.getCode(), err.getMessage(), "");
    }

    public static Result<String> fail(int code, String msg) {
        return new Result<>(code, msg, "");
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, "", data);
    }

    public static Result<String> success() {
        return new Result<>(SUCCESS_CODE, "", "");
    }

    public boolean isSuccess() {
        return code == SUCCESS_CODE;
    }

    /**
     * 获取结果，如果失败则抛出异常
     */
    public T unWrapper() {
        if (getCode() != SUCCESS_CODE) {
            throw new ServiceException(getCode(), getMsg());
        } else {
            return getData();
        }
    }

}
