package com.bruce.phoenix.sys.log;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bruce.phoenix.common.model.common.Result;
import com.bruce.phoenix.common.model.enums.BusinessStatusEnum;
import com.bruce.phoenix.sys.model.po.SysLog;
import com.bruce.phoenix.sys.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/2/7 19:14
 * @Author Bruce
 */
@Aspect
@Component
@Slf4j
public class LogRecordAspect {

    @Resource
    private SysLogService sysLogService;
    @Resource
    private HttpServletRequest request;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.bruce.phoenix.sys.log.LogRecord)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            // 获得注解
            LogRecord controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            // 拼装日志参数
            SysLog sysLog = new SysLog();
            sysLog.setStatus(BusinessStatusEnum.SUCCESS.getCode());
            String ip = ServletUtil.getClientIP(request);
            sysLog.setOperIp(ip);
            sysLog.setOperUrl(request.getRequestURI());
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            sysLog.setMethod(className + "." + methodName + "()");
            sysLog.setModule(controllerLog.module());
            sysLog.setBusinessType(controllerLog.businessType().getCode());
            sysLog.setOperatorType(controllerLog.operatorType().getCode());
            // 设置请求方式
            sysLog.setRequestMethod(request.getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, sysLog);

            if (controllerLog.isSaveResponseData()) {
                // 返回参数
                sysLog.setOperResult(JSONObject.toJSONString(jsonResult));
            }

            if (controllerLog.isSaveUser()) {
                // todo  获取登录用户信息
                sysLog.setCreateUser("");
            }

            // 异常信息处理
            try {
                // 正常返回都可以解析为 Result
                Result result = JSONObject.parseObject(JSONObject.toJSONString(jsonResult), Result.class);
                // 业务异常
                if (result.getCode() != Result.success().getCode()) {
                    sysLog.setStatus(BusinessStatusEnum.FAIL.getCode());
                    sysLog.setErrorMsg(StrUtil.sub(result.getMsg(), 0, Math.min(result.getMsg().length(), 2000)));
                }
            } catch (Exception ex) {
                // 如果是 http 异常
                if (e != null) {
                    sysLog.setStatus(BusinessStatusEnum.FAIL.getCode());
                    sysLog.setErrorMsg(StrUtil.sub(e.getMessage(), 0, Math.min(e.getMessage().length(), 2000)));
                } else {
                    // 兜底，理论上不会走到这
                    sysLog.setStatus(BusinessStatusEnum.FAIL.getCode());
                    sysLog.setErrorMsg(StrUtil.sub(ex.getMessage(), 0, Math.min(ex.getMessage().length(), 2000)));
                }
            }

            ThreadUtil.execute(() -> {
                sysLogService.log(sysLog);
            });

        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("错误={}", exp.getMessage(), e);
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param logRecord    日志注解
     * @param operationLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, LogRecord logRecord, SysLog operationLog) throws Exception {
        // 是否需要保存request，参数和值
        if (logRecord.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operationLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operationLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SysLog operationLog) throws Exception {
        String requestMethod = operationLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operationLog.setOperParam(params);
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operationLog.setOperParam(paramsMap.toString());
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private LogRecord getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(LogRecord.class);
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null) {
            for (Object o : paramsArray) {
                if (!isFilterObject(o)) {
                    Object jsonObj = JSON.toJSON(o);
                    params.append(jsonObj.toString()).append(" ");
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }
}
