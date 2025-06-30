package com.bruce.phoenix.sys.openapi;

import com.bruce.phoenix.common.exception.CommonException;
import com.bruce.phoenix.common.model.constants.CommonConstant;
import com.bruce.phoenix.core.component.limit.RedisZSetLimiterComponent;
import com.bruce.phoenix.core.component.middleware.RedisComponent;
import com.bruce.phoenix.core.util.GatewayHttpUtil;
import com.bruce.phoenix.sys.model.po.SysOpenapi;
import com.bruce.phoenix.sys.service.SysOpenapiService;
import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/6/27 10:39
 * @Author Bruce
 */
// @Component
@Aspect
@Slf4j
@Configuration
@ConditionalOnProperty(name = "openapi.enable", havingValue = "true")
public class OpenApiAdvice {

    @Resource
    private HttpServletRequest request;
    @Resource
    private SysOpenapiService sysOpenapiService;
    @Resource
    private RedisComponent redisComponent;


    private final String KEY = "openapi:{0}:{1}";

    /**
     * 表示匹配带有自定义注解的方法
     */
    @Pointcut("@annotation(com.bruce.phoenix.sys.openapi.OpenApi)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 获得注解
        OpenApi openApi = getAnnotationLog(point);
        if (openApi == null) {
            return point.proceed();
        }

        // 供应商的id，验证用户的真实性
        String accessKey = request.getHeader(CommonConstant.ACCESS_KEY);
        // 请求发起的时间
        String timestamp = request.getHeader(CommonConstant.TIMESTAMP);
        // 签名算法生成的签名
        String sign = request.getHeader(CommonConstant.SIGN);
        String path = request.getServletPath();
        if (StringUtil.isEmpty(accessKey) || StringUtil.isEmpty(timestamp) || StringUtil.isEmpty(path) || StringUtil.isEmpty(sign)) {
            throw new CommonException("签名参数错误!");
        }
        // 限制为（含）5分钟以内发送的请求
        long time = 5 * 60;
        long now = System.currentTimeMillis() / 1000;
        if (now - Long.parseLong(timestamp) > time) {
            throw new CommonException("请求发起时间超过服务器限制时间!");
        }
        // 查询appid是否正确
        SysOpenapi sysOpenapi = sysOpenapiService.queryByAccessKey(accessKey);
        if (sysOpenapi == null) {
            throw new CommonException("签名参数错误!");
        }

        String key = MessageFormat.format(KEY, accessKey, timestamp);
        // 一秒执行一次
        RedisZSetLimiterComponent limiterComponent = new RedisZSetLimiterComponent(redisComponent, 1, 1000);
        if (limiterComponent.isLimited(key)) {
            throw new CommonException("请求过于频繁!");
        }

        Map<String, String> map = new HashMap<>(4);
        map.put(CommonConstant.TIMESTAMP, timestamp);
        map.put(CommonConstant.ACCESS_KEY, accessKey);
        map.put(CommonConstant.PATH, path);
        map.put(CommonConstant.VERSION, openApi.version());
        String mySign = GatewayHttpUtil.generateSign(map, sysOpenapi.getAccessSecret());
        // 验证签名
        if (!mySign.equals(sign)) {
            throw new CommonException("签名信息错误!");
        }
        return point.proceed();
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private OpenApi getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(OpenApi.class);
        }
        return null;
    }

}
