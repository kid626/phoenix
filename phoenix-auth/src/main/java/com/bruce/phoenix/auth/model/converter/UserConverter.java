package com.bruce.phoenix.auth.model.converter;

import com.bruce.phoenix.auth.model.po.User;
import com.bruce.phoenix.auth.model.form.UserForm;
import com.bruce.phoenix.auth.model.vo.UserVO;
import org.springframework.beans.BeanUtils;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 转换层
 * @ProjectName phoenix-auth
 * @Date 2024-03-05
 * @Author Bruce
 */
public class UserConverter {

    public static User convert2Po(UserForm form) {
        User po = new User();
        BeanUtils.copyProperties(form, po);
        return po;
    }

    public static UserVO convert2Vo(User po) {
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }



}

