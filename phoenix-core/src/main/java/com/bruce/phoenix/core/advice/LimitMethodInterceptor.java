package com.bruce.phoenix.core.advice;

import com.bruce.phoenix.common.exception.CommonException;
import com.bruce.phoenix.core.annotation.Limiter;
import com.bruce.phoenix.core.component.limit.RedisZSetLimiterComponent;
import com.bruce.phoenix.core.component.middleware.RedisComponent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/2/29 15:58
 * @Author Bruce
 */
@Aspect
@Configuration
@Slf4j
public class LimitMethodInterceptor {

    @Resource
    private RedisComponent redisComponent;

    @Around("execution(public * *(..))  && @annotation(com.bruce.phoenix.core.annotation.Limiter)")
    public Object proceed(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Limiter limiter = method.getAnnotation(Limiter.class);
        String key = getKey(limiter.key(), joinPoint.getArgs());
        log.info("key={}", key);
        RedisZSetLimiterComponent limiterComponent = new RedisZSetLimiterComponent(redisComponent, limiter.count(), limiter.expire());
        if (limiterComponent.isLimited(key)) {
            throw new CommonException(limiter.message());
        }
        return joinPoint.proceed();
    }

    private String getKey(String keyExpress, Object[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                keyExpress = keyExpress.replace("{" + i + "}", args[i].toString());
            }
        }
        return keyExpress;
    }

}
