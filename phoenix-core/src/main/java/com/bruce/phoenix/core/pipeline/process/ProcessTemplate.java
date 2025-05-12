package com.bruce.phoenix.core.pipeline.process;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/5/12 14:07
 * @Author Bruce
 */
@Data
@ApiModel("业务执行模板（把责任链的逻辑串起来）")
public class ProcessTemplate<T> {

    private List<BusinessProcess<T>> processList;


}
