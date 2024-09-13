package com.bruce.phoenix.core.token.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.bruce.phoenix.core.token.model.BaseTokenModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/13 15:04
 * @Author Bruce
 */
@Slf4j
@Service
public class DefaultTokenService implements ITokenService {

    @Override
    public BaseTokenModel getToken() {
        BaseTokenModel baseTokenModel = new BaseTokenModel();
        baseTokenModel.setToken(RandomUtil.randomString(16));
        baseTokenModel.setExpireTime(DateUtil.date().offset(DateField.SECOND, 150).getTime());
        return baseTokenModel;
    }

}
