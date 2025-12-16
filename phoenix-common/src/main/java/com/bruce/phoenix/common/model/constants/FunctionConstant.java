package com.bruce.phoenix.common.model.constants;

import com.bruce.phoenix.common.util.StreamUtil;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/12/26 14:30
 * @Author Bruce
 */
public final class FunctionConstant {

    private FunctionConstant() {}

    /**
     * 合并两个long类型
     */
    public final static BiFunction<? super Long, ? super Long, ? extends Long> MERGE_LONG = Long::sum;

    /**
     * 合并两个integer类型
     */
    public final static BiFunction<? super Integer, ? super Integer, ? extends Integer> MERGE_INTEGER = Integer::sum;

    /**
     * 合并两个map
     */
    public final static BiFunction<Map<String, Long>, Map<String, Long>, Map<String, Long>> MERGE_MAP = StreamUtil::merge;


    /**
     * 替换
     */
    public final static BiFunction<?, ?, ?> REPLACE_FUNCTION = (existing, replacing) -> replacing;

}
