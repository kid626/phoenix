package com.bruce.phoenix.common.util;

import cn.hutool.core.collection.CollUtil;
import lombok.experimental.UtilityClass;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/7/3 14:27
 * @Author Bruce
 */
@UtilityClass
public class PhoenixCollUtil extends CollUtil {

    /**
     * 获取字符串，为空则返回默认值
     *
     * @param list        集合
     * @param defaultList 默认集合  不能为 null，否则没有意义！
     */
    public static <T> List<T> getOrDefault(List<T> list, @NotNull List<T> defaultList) {
        return isEmpty(list) ? defaultList : list;
    }


}
