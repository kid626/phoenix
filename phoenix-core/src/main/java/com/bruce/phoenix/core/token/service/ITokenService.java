package com.bruce.phoenix.core.token.service;

import com.bruce.phoenix.common.model.constants.CommonConstant;
import com.bruce.phoenix.core.token.model.BaseTokenModel;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/13 15:04
 * @Author Bruce
 */
public interface ITokenService {

    default String getType() {
        return CommonConstant.DEFAULT;
    }

    /**
     * 获取token
     */
    BaseTokenModel getToken();

}
