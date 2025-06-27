package com.bruce.phoenix.sys.openapi;

import com.bruce.phoenix.common.exception.CommonException;
import com.bruce.phoenix.common.model.constants.CommonConstant;
import com.bruce.phoenix.core.component.middleware.RedisComponent;
import com.bruce.phoenix.core.util.GatewayHttpUtil;
import com.bruce.phoenix.sys.model.po.SysOpenapi;
import com.bruce.phoenix.sys.service.SysOpenapiService;
import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/6/27 10:39
 * @Author Bruce
 */
@Component
@Aspect
@Slf4j
public class OpenApiAdvice {

    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;
    @Resource
    private RedisComponent redisComponent;
    @Resource
    private SysOpenapiService sysOpenapiService;


    /**
     * 表示匹配带有自定义注解的方法
     */
    @Pointcut("@annotation(com.bruce.phoenix.sys.openapi.OpenApi)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 供应商的id，验证用户的真实性
        String accessKey = request.getHeader(CommonConstant.ACCESS_KEY);
        // 请求发起的时间
        String timestamp = request.getHeader(CommonConstant.TIMESTAMP);
        // 签名算法生成的签名
        String sign = request.getHeader(CommonConstant.SIGN);
        if (StringUtil.isEmpty(accessKey) || StringUtil.isEmpty(timestamp) || StringUtil.isEmpty(sign)) {
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
        Map<String, String> map = new HashMap<>(4);
        map.put(CommonConstant.TIMESTAMP, timestamp);
        map.put(CommonConstant.ACCESS_KEY, accessKey);
        String mySign = GatewayHttpUtil.generateSign(map, sysOpenapi.getAccessSecret());
        // 验证签名
        if (!mySign.equals(sign)) {
            throw new CommonException("签名信息错误!");
        }
        return point.proceed();


    }
}
