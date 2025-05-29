package com.bruce.phoenix.core.config.mybatisplus;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

import java.util.List;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/5/29 14:27
 * @Author Bruce
 */
public class BatchSaveSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        // super.getMethodList() 保留 Mybatis Plus 自带的方法
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        // 添加自定义方法：批量插入，方法名为 insertBatchSomeColumn
        methodList.add(new BatchSaveMethod());
        return methodList;
    }

}
