package com.bruce.phoenix.core.quartz.model;

import com.bruce.phoenix.common.annotation.DateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quartz.Job;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/5/8 10:30
 * @Author Bruce
 */
@Data
@ApiModel("定时任务信息模型")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobInfoModel {

    @ApiModelProperty("任务类名")
    private String jobClassName;

    @ApiModelProperty("任务类名,二选一")
    private Class<? extends Job> jobClass;

    @ApiModelProperty("cron表达式")
    private String cronExpression;

    @ApiModelProperty("频率，单位秒")
    private Integer interval;

    @ApiModelProperty("次数,-1代表一直重复")
    private Integer repeatCount = -1;

    @ApiModelProperty("参数")
    private String parameter;

    @ApiModelProperty("任务名称")
    private String jobName;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("任务状态")
    private String jobStatus;

    @ApiModelProperty("任务开始时间 yyyy-MM-dd HH:mm:ss")
    @DateTime(format = "yyyy-MM-dd HH:mm:ss")
    private String startTime;

    @ApiModelProperty("任务结束时间 yyyy-MM-dd HH:mm:ss")
    @DateTime(format = "yyyy-MM-dd HH:mm:ss")
    private String endTime;

}
