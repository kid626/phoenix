package com.bruce.phoenix.auth.model.convert;

import com.bruce.phoenix.auth.model.po.UserRole;
import com.bruce.phoenix.auth.model.form.UserRoleForm;
import com.bruce.phoenix.auth.model.vo.UserRoleVO;
import org.springframework.beans.BeanUtils;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 转换层
 * @ProjectName phoenix-auth
 * @Date 2024-03-05
 * @Author Bruce
 */
public class UserRoleConverter {

    public static UserRole convert2Po(UserRoleForm form) {
        UserRole po = new UserRole();
        BeanUtils.copyProperties(form, po);
        return po;
    }

    public static UserRoleVO convert2Vo(UserRole po) {
        UserRoleVO vo = new UserRoleVO();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }



}

