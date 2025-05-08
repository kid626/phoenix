package com.bruce.phoenix.core.quartz.component;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.bruce.phoenix.core.quartz.model.JobInfoModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicom.middleware.unicom.common.exception.ServiceException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.MutableTrigger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

import static org.quartz.Trigger.TriggerState.*;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc 包含定时任务添加，修改，运行，暂停，恢复，删除和查询
 * @ProjectName phoenix
 * @Date 2025/5/8 10:25
 * @Author Bruce
 */
@Component
@Slf4j
public class QuartzComponent {

    @Resource
    private Scheduler scheduler;
    @Value("$spring.application.name:jobGroup")
    private String jobGroup;

    private static final Map<Trigger.TriggerState, String> TRIGGER_STATE;

    public static final String END_JOB_SUFFIX = ":endTask";

    static {
        TRIGGER_STATE = new HashMap<>();
        TRIGGER_STATE.put(NONE, "空");
        TRIGGER_STATE.put(NORMAL, "正常");
        TRIGGER_STATE.put(PAUSED, "暂停");
        TRIGGER_STATE.put(COMPLETE, "完成");
        TRIGGER_STATE.put(ERROR, "错误");
        TRIGGER_STATE.put(BLOCKED, "阻塞");
    }

    /**
     * 创建定时任务
     *
     * @param model 定时任务信息模型
     */
    @SneakyThrows
    public void createScheduleJob(JobInfoModel model) {

        if (model.getJobClass() == null && StrUtil.isBlank(model.getJobClassName())) {
            throw new ServiceException("jobClass 和 jobClassName 不能同时为空");
        }

        if (model.getInterval() == null && StrUtil.isBlank(model.getCronExpression())) {
            throw new ServiceException("interval 和 cronExpression 不能同时为空");
        }

        String jobName = model.getJobName();
        if (StrUtil.isBlank(jobName)) {
            jobName = RandomUtil.randomString(16) + DateUtil.format(DateUtil.date(), DatePattern.PURE_DATETIME_PATTERN);
        }

        // 获取定时任务的执行类
        // 定时任务类需要是job类的具体实现 QuartzJobBean是job的抽象类
        Class<? extends Job> jobClass = model.getJobClass() == null ?
                (Class<? extends Job>) Class.forName(model.getJobClassName()) : model.getJobClass();
        // 构建定时任务信息
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobKey).build();

        // 如果参数不为空，写入参数
        ObjectMapper mapper = new ObjectMapper();
        if (StrUtil.isNotEmpty(model.getParameter())) {
            Map param = mapper.readValue(model.getParameter(), Map.class);
            jobDetail.getJobDataMap().putAll(param);
        }

        // 构建触发器trigger
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);

        ScheduleBuilder<? extends Trigger> scheduleBuilder = null;
        // 优先 使用cron 表达式
        if (StrUtil.isNotBlank(model.getCronExpression())) {
            // 设置定时任务的执行方式
            scheduleBuilder = CronScheduleBuilder.cronSchedule(model.getCronExpression());
        } else {
            // 如果没有使用cron表达式，使用简单表达式
            // 简单的调度计划的构造器
            scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(model.getInterval())
                    .withRepeatCount(model.getRepeatCount());
        }
        // 构建触发器trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withDescription(model.getDescription())
                .withSchedule(scheduleBuilder)
                .startAt(StrUtil.isBlank(model.getStartTime()) ? DateUtil.date() : DateUtil.parse(model.getStartTime(), DatePattern.NORM_DATETIME_PATTERN))
                .endAt(StrUtil.isBlank(model.getEndTime()) ? DateUtil.date().offset(DateField.YEAR, 100) : DateUtil.parse(model.getEndTime(), DatePattern.NORM_DATETIME_PATTERN))
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 根据任务名称暂停定时任务
     *
     * @param jobName 定时任务名称
     */
    @SneakyThrows
    public void pauseScheduleJob(String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        scheduler.pauseJob(jobKey);
    }

    /**
     * 根据任务名称恢复定时任务
     *
     * @param jobName 定时任务名称
     */
    @SneakyThrows
    public void resumeScheduleJob(String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        scheduler.resumeJob(jobKey);
    }

    /**
     * 根据任务名称运行一次定时任务
     *
     * @param jobName 定时任务名称
     */
    @SneakyThrows
    public void runOnce(String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        scheduler.triggerJob(jobKey);
    }


    /**
     * 删除定时任务
     *
     * @param jobName 定时任务名称
     */
    @SneakyThrows
    public void deleteScheduleJob(String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        scheduler.deleteJob(jobKey);
    }

    /**
     * 查询所有定时任务
     */
    @SneakyThrows
    public List<JobInfoModel> findAll() {
        GroupMatcher<JobKey> matcher;
        if (StrUtil.isBlank(jobGroup)) {
            matcher = GroupMatcher.anyGroup();
        } else {
            matcher = GroupMatcher.groupEquals(jobGroup);
        }

        List<JobInfoModel> list = new ArrayList<>();
        // 遍历所有 job
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        for (JobKey jobKey : jobKeys) {
            // 构建 model
            JobInfoModel jobInfo = new JobInfoModel();

            // 1 获取定时任务信息
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            // 1.1 获取定时任务类
            jobInfo.setJobClass(jobDetail.getJobClass());
            jobInfo.setJobClassName(jobDetail.getJobClass().getName());
            // 1.2 获取定时任务参数
            JobDataMap map = jobDetail.getJobDataMap();
            jobInfo.setParameter(JSONObject.toJSONString(map));
            // 1.3 获取定时任务名称
            jobInfo.setJobName(jobKey.getName());

            // 2 获取定时任务触发器信息
            TriggerKey triggerKey = TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup());
            Trigger trigger = scheduler.getTrigger(triggerKey);
            // 2.1 如果是 cron 表达式
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                jobInfo.setDescription(cronTrigger.getDescription());
                jobInfo.setCronExpression(cronTrigger.getCronExpression());
                jobInfo.setStartTime(DateUtil.format(cronTrigger.getStartTime(), DatePattern.NORM_DATETIME_PATTERN));
                jobInfo.setEndTime(DateUtil.format(cronTrigger.getEndTime(), DatePattern.NORM_DATETIME_PATTERN));
            } else {
                SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
                jobInfo.setDescription(simpleTrigger.getDescription());
                jobInfo.setInterval((int) simpleTrigger.getRepeatInterval());
                jobInfo.setRepeatCount(simpleTrigger.getRepeatCount());
                jobInfo.setStartTime(DateUtil.format(simpleTrigger.getStartTime(), DatePattern.NORM_DATETIME_PATTERN));
                jobInfo.setEndTime(DateUtil.format(simpleTrigger.getEndTime(), DatePattern.NORM_DATETIME_PATTERN));
            }
            // 2.2 获取定时任务状态
            jobInfo.setJobStatus(getJobStatus(triggerKey));

            list.add(jobInfo);
        }
        return list;
    }

    /**
     * 获取定时任务状态
     *
     * @param triggerKey 触发器
     */
    @SneakyThrows
    public String getJobStatus(TriggerKey triggerKey) {
        Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
        return TRIGGER_STATE.get(triggerState);
    }


    /**
     * 错过触发策略：即错过了定时任务的开始时间可以做什么，此处设置什么也不做，即过了开始时间也不执行
     * 如果不设置，默认为立即触发一次，例如设置定时任务开始时间为10点，没有设置结束时间，在10点后重启系统，
     * 如果不设置为CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING，那么开始时间在重启前的定时任务会全部重新触发一次
     *
     * @param trigger Trigger
     */
    private static void chgMisFire(Trigger trigger) {
        // 修改 misfire 策略
        if (trigger instanceof MutableTrigger) {
            MutableTrigger mutableTrigger = (MutableTrigger) trigger;
            mutableTrigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
        }
    }

}
