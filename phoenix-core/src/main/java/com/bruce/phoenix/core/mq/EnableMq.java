package com.bruce.phoenix.core.mq;

import com.bruce.phoenix.core.mq.listener.MqListener;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/13 19:36
 * @Author Bruce
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({MqListener.class})
@Documented
public @interface EnableMq {
}
