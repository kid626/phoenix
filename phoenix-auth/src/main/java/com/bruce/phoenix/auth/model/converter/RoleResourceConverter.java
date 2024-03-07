package com.bruce.phoenix.auth.model.converter;

import com.bruce.phoenix.auth.model.po.RoleResource;
import com.bruce.phoenix.auth.model.form.RoleResourceForm;
import com.bruce.phoenix.auth.model.vo.RoleResourceVO;
import org.springframework.beans.BeanUtils;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 转换层
 * @ProjectName phoenix-auth
 * @Date 2024-03-05
 * @Author Bruce
 */
public class RoleResourceConverter {

    public static RoleResource convert2Po(RoleResourceForm form) {
        RoleResource po = new RoleResource();
        BeanUtils.copyProperties(form, po);
        return po;
    }

    public static RoleResourceVO convert2Vo(RoleResource po) {
        RoleResourceVO vo = new RoleResourceVO();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }



}

