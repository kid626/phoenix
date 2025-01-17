package com.bruce.demo.web.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/2/28 16:58
 * @Author Bruce
 */
@Slf4j
@Component
public class ThreadTask {

    @Scheduled(cron = "0 14 16 ? * ?")
    public void hello() {
        log.info("hello world");
        int a = 1 / 0;

    }

}
