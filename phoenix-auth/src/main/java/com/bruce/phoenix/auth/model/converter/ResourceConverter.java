package com.bruce.phoenix.auth.model.converter;

import com.bruce.phoenix.auth.model.po.Resource;
import com.bruce.phoenix.auth.model.form.ResourceForm;
import com.bruce.phoenix.auth.model.vo.ResourceVO;
import org.springframework.beans.BeanUtils;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 转换层
 * @ProjectName phoenix-auth
 * @Date 2024-03-05
 * @Author Bruce
 */
public class ResourceConverter {

    public static Resource convert2Po(ResourceForm form) {
        Resource po = new Resource();
        BeanUtils.copyProperties(form, po);
        return po;
    }

    public static ResourceVO convert2Vo(Resource po) {
        ResourceVO vo = new ResourceVO();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }



}

