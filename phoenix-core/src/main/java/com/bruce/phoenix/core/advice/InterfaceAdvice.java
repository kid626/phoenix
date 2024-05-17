package com.bruce.phoenix.core.advice;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.bruce.phoenix.common.exception.CommonException;
import com.bruce.phoenix.common.model.common.Err;
import com.bruce.phoenix.common.model.constants.CommonConstant;
import com.unicom.middleware.unicom.common.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc
 * @ProjectName demo
 * @Date 2022/6/17 9:47
 * @Author fzh
 */
@Aspect
@Component
@Slf4j
public class InterfaceAdvice {

    /**
     * controller 层切面
     */
    // @Around("execution(public * com.bruce.*.*.controller..*(..))")
    @Around("logCut()")
    public Object ajaxResultProcess(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String traceId = UUID.fastUUID().toString(true);
        MDC.put(CommonConstant.TRACE_ID, traceId);

        Object obj;
        String fun = fetchFun(pjp);
        try {
            log.info("[op:trace],fun={},args={}", fun, argsArrayToString(pjp.getArgs()));
            obj = pjp.proceed();
            log.info("[op:trace],fun={},result=success,elapse={}ms", fun, stopWatch.getTotalTimeMillis());
            return obj;
        } catch (CommonException e) {
            log.info("[op:trace],fun={},result=fail,elapse={}ms,err={}", fun, stopWatch.getTotalTimeMillis(), e.getMessage());
            return Result.fail(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("[op:trace],fun={},result=fail,elapse={}ms", fun, stopWatch.getTotalTimeMillis(), e);
            return Result.fail(Err.SYS_ERROR.getCode(), e.getMessage());
        } finally {
            stopWatch.stop();
            MDC.remove(CommonConstant.TRACE_ID);
            MDC.remove(CommonConstant.SESSION);
            MDC.remove(CommonConstant.USER_ID);
            MDC.clear();
        }
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PutMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void logCut() {}

    private String fetchFun(ProceedingJoinPoint pjp) {
        return "[" + pjp.getSignature().getDeclaringType().getSimpleName() + "." + pjp.getSignature().getName() + "]";
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder("[");
        if (paramsArray != null) {
            for (Object o : paramsArray) {
                if (!isFilterObject(o)) {
                    String jsonObj = JSONUtil.toJsonStr(o);
                    params.append(jsonObj).append(",");
                }
            }
        }
        params.append("]");
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o) {
        return o == null || o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }

}
