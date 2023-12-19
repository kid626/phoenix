package com.bruce.phoenix.core.advice;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.lang.UUID;
import com.bruce.phoenix.common.exception.CommonException;
import com.bruce.phoenix.common.model.common.Err;
import com.bruce.phoenix.common.model.common.Result;
import com.bruce.phoenix.common.model.constants.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

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
            log.info("[op:trace],fun={},args={}", fun, pjp.getArgs());
            obj = pjp.proceed();
            log.info("[op:trace],fun={},result=success,elapse={}ms", fun, stopWatch.getTotalTimeMillis());
            return obj;
        } catch (CommonException e) {
            log.info("[op:trace],fun={},result=fail,elapse={}ms,err={}", fun, stopWatch.getTotalTimeMillis(), e.getMessage());
            return Result.fail(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("[op:trace],fun={},result=fail,elapse={}ms", fun, stopWatch.getTotalTimeMillis(), e);
            return Result.fail(Err.SYS_ERROR);
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

}
