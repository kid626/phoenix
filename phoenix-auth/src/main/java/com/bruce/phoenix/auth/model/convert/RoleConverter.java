package com.bruce.phoenix.auth.model.convert;

import com.bruce.phoenix.auth.model.po.Role;
import com.bruce.phoenix.auth.model.form.RoleForm;
import com.bruce.phoenix.auth.model.vo.RoleVO;
import org.springframework.beans.BeanUtils;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 转换层
 * @ProjectName phoenix-auth
 * @Date 2024-03-05
 * @Author Bruce
 */
public class RoleConverter {

    public static Role convert2Po(RoleForm form) {
        Role po = new Role();
        BeanUtils.copyProperties(form, po);
        return po;
    }

    public static RoleVO convert2Vo(Role po) {
        RoleVO vo = new RoleVO();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }



}

