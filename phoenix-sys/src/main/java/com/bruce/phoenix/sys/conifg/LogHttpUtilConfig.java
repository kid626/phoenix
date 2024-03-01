package com.bruce.phoenix.sys.conifg;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.GlobalInterceptor;
import cn.hutool.json.JSONUtil;
import com.bruce.phoenix.common.util.ThreadLocalUtil;
import com.bruce.phoenix.sys.model.form.SysApiLogForm;
import com.bruce.phoenix.sys.service.SysApiLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc {@link com.bruce.phoenix.common.config.HttpUtilConfig 增强,日志记录持久化}
 * @ProjectName phoenix
 * @Date 2024/3/1 13:43
 * @Author Bruce
 */
@Configuration
@Slf4j
public class LogHttpUtilConfig {

    @Resource
    private SysApiLogService sysApiLogService;

    @PostConstruct
    public void init() {
        GlobalInterceptor.INSTANCE.addRequestInterceptor(request -> {
            String requestId = ThreadLocalUtil.getRequestId();
            SysApiLogForm form = SysApiLogForm.builder()
                    .requestId(requestId)
                    .requestUrl(request.getUrl())
                    .requestHeaders(JSONUtil.toJsonStr(request.headers()))
                    .requestMethod(request.getMethod().name())
                    .requestForm(JSONUtil.toJsonStr(request.form()))
                    .requestTime(DateUtil.date())
                    .build();
            sysApiLogService.save(form);
        });
        GlobalInterceptor.INSTANCE.addResponseInterceptor(response -> {
            String requestId = ThreadLocalUtil.getRequestId();
            SysApiLogForm form = SysApiLogForm.builder()
                    .requestId(requestId)
                    .responseCode(response.getStatus() + "")
                    .responseBody(response.body()).build();
            sysApiLogService.update(form);
        });
    }


}
