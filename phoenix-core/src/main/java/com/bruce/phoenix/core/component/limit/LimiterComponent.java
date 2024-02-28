package com.bruce.phoenix.core.component.limit;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/2/28 16:02
 * @Author Bruce
 */
public interface LimiterComponent {

    /**
     * 是否限流
     *
     * @param key key
     * @return 是否限流
     */
    boolean isLimited(String key);

}
