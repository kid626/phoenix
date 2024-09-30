package com.bruce.phoenix.oss.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.bruce.phoenix.common.exception.CommonException;
import com.bruce.phoenix.common.model.common.Err;
import com.bruce.phoenix.core.model.oss.OssFileInfoModel;
import com.bruce.phoenix.oss.model.constants.OssConstant;
import com.bruce.phoenix.oss.util.JWTUtil;
import com.unicom.middleware.unicom.common.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/29 17:42
 * @Author Bruce
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new OssInterceptor()).addPathPatterns("/oss/**").excludePathPatterns("/oss/v1/token");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    public static class OssInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            // 获取token
            String token = request.getHeader(OssConstant.UPLOAD_TOKEN);
            if (StrUtil.isNotBlank(token)) {
                // 解析token
                OssFileInfoModel model = JWTUtil.verifyToken(token);
                log.info("model：{}", JSONUtil.toJsonStr(model));
                // 暂存
                JWTUtil.setToken(model);
                return true;

            }
            PrintWriter writer;
            try {
                writer = response.getWriter();
            } catch (IOException e) {
                throw new CommonException(e.getMessage());
            }
            Result<String> result = Result.fail(Err.NO_AUTH.getCode(), Err.NO_AUTH.getMessage());
            writer.write(JSONUtil.toJsonStr(result));
            return false;

        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
            // 删除暂存
            JWTUtil.removeToken();
        }
    }


}
