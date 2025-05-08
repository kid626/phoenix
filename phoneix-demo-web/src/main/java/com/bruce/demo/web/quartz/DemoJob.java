package com.bruce.demo.web.quartz;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/5/8 09:40
 * @Author Bruce
 */
//Job中的任务有可能并发执行，例如任务的执行时间过长，而每次触发的时间间隔太短，则会导致任务会被并发执行。如果是并发执行，就需要一个数据库锁去避免一个数据被多次处理。
@DisallowConcurrentExecution
@Slf4j
public class DemoJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String now = DateUtil.format(DateUtil.date(), DatePattern.NORM_DATETIME_PATTERN);
        log.info("[DemoJob#executeInternal] now:{} jobName:{} params:{}", now, jobDetail.getKey().getName(), JSONUtil.toJsonStr(jobDataMap));
    }
}
