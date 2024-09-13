package com.bruce.phoenix.common.util;

import com.bruce.phoenix.common.model.enums.ScreenTypeEnum;
import com.bruce.phoenix.common.model.screen.*;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/13 14:30
 * @Author Bruce
 */
@UtilityClass
public class ScreenUtil {

    public static <T> ScreenPieChartVO<T> buildPieChartVO(List<ScreenNameValueVO<T>> list) {
        List<List<ScreenBaseDataVO<ScreenNameValueVO<T>>>> series = new ArrayList<>();
        ScreenBaseDataVO<ScreenNameValueVO<T>> screenBaseDataVO = ScreenBaseDataVO.<ScreenNameValueVO<T>>builder()
                .type(ScreenTypeEnum.PIE.getCode())
                .data(list)
                .build();
        series.add(Arrays.asList(screenBaseDataVO));
        return ScreenPieChartVO.<T>builder().series(series).build();
    }

    public static <T> ScreenBarChartVO<T> buildBarChartVO(List<ScreenBaseDataVO<T>> list, List<String> xAxis) {
        return ScreenBarChartVO.<T>builder()
                .series(Arrays.asList(list))
                .xAxis(xAxis)
                .build();
    }

    public static <T> ScreenLineChartVO<T> buildLineChartVO(List<ScreenBaseDataVO<T>> list, List<String> xAxis) {
        return ScreenLineChartVO.<T>builder()
                .series(Arrays.asList(list))
                .xAxis(xAxis)
                .build();
    }

}
