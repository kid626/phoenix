package com.bruce.phoenix.core.token;

import com.bruce.phoenix.core.token.component.TokenComponent;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/13 19:41
 * @Author Bruce
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({TokenComponent.class})
@Documented
public @interface EnableToken {
}
