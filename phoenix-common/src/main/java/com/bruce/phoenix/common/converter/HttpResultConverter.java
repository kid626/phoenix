package com.bruce.phoenix.common.converter;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.bruce.phoenix.common.model.common.HttpBaseResp;
import com.unicom.middleware.unicom.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Copyright Copyright © 2025 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2025/8/4 10:24
 * @Author Bruce
 */
@Slf4j
public abstract class HttpResultConverter<R extends HttpBaseResp> {

    public String resultToData(String resp) {
        log.info("[HttpResultConverter#resultToData] resp={}", resp);
        if (StrUtil.isBlank(resp)) {
            throw new ServiceException("resp 不能为空!");
        }
        R r = JSONUtil.toBean(resp, new TypeReference<R>() {}, false);
        if (!r.isSuccess()) {
            throw new ServiceException(r.getMessage());
        }
        return r.getData();
    }

    public <T> T resultToObject(String result, Class<T> clazz) {
        return JSONUtil.toBean(resultToData(result), clazz);
    }

    public <T> List<T> resultToList(String result, Class<T> clazz) {
        return JSONUtil.toList(resultToData(result), clazz);
    }

}
