package com.bruce.phoenix.core.advice;

import com.bruce.phoenix.common.model.common.Err;
import com.bruce.phoenix.common.model.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.Set;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2023/12/19 15:16
 * @Author Bruce
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    /**
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常
     *
     * @param e MethodArgumentNotValidException
     * @return 返回请求方
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        StringBuilder sb = new StringBuilder("Invalid Request:");
        for (FieldError fieldError : result.getFieldErrors()) {
            sb.append(fieldError.getField()).append("=[").append(fieldError.getRejectedValue()).append("]").append(fieldError.getDefaultMessage()).append(";");
        }
        return Result.fail(Err.PARAM_ERROR.getCode(), sb.toString());
    }

    /**
     * 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     *
     * @param ex BindException
     * @return 返回请求方
     */
    @ExceptionHandler(BindException.class)
    public Result<String> handleBindException(BindException ex) {
        BindingResult result = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("Invalid Request:");
        for (FieldError fieldError : result.getFieldErrors()) {
            sb.append(fieldError.getField()).append("=[").append(fieldError.getRejectedValue()).append("]").append(fieldError.getDefaultMessage()).append(";");
        }
        // 生成返回结果
        return Result.fail(Err.PARAM_ERROR.getCode(), sb.toString());
    }

    /**
     * 使用@Valid 验证service层校验失败抛出的异常
     *
     * @param ex BindException
     * @return 返回请求方
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<String> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> result = ex.getConstraintViolations();
        StringBuilder sb = new StringBuilder("Invalid Request:");
        for (ConstraintViolation<?> error : result) {
            sb.append(error.getMessage()).append(";");
        }
        // 生成返回结果
        return Result.fail(Err.PARAM_ERROR.getCode(), sb.toString());
    }

    /**
     * sql 错误拦截
     *
     * @return 返回请求方
     */
    @ExceptionHandler(value = {SQLException.class, SQLSyntaxErrorException.class, DataAccessException.class, PersistenceException.class})
    public Result<String> handleSqlException() {
        return Result.fail(Err.SQL_ERROR.getCode(), Err.SQL_ERROR.getMessage());
    }


}
