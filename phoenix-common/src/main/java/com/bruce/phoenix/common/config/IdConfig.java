package com.bruce.phoenix.common.config;

import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import com.dangdang.ddframe.rdb.sharding.id.generator.self.IPIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName mp-quickstart
 * @Date 2023/11/30 16:48
 * @Author Bruce
 */
@Configuration
public class IdConfig {

    @Bean
    public IdGenerator idGenerator() {
        return new IPIdGenerator();
    }

}
