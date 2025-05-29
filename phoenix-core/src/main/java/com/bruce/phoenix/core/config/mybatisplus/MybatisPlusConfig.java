package com.bruce.phoenix.core.config.mybatisplus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/5/29 14:38
 * @Author Bruce
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public BatchSaveSqlInjector batchSaveSqlInjector() {
        return new BatchSaveSqlInjector();
    }

}
