package com.bruce.demo.web.model.convert;

import com.bruce.demo.web.model.po.DemoUser;
import com.bruce.demo.web.model.form.DemoUserForm;
import com.bruce.demo.web.model.vo.DemoUserVO;
import org.springframework.beans.BeanUtils;

/**
 * @Copyright Copyright © 2023 Bruce . All rights reserved.
 * @Desc 转换层
 * @ProjectName phoenix-demo
 * @Date 2023-12-18
 * @Author Bruce
 */
public class DemoUserConverter {

    public static DemoUser convert2Po(DemoUserForm form) {
        DemoUser po = new DemoUser();
        BeanUtils.copyProperties(form, po);
        return po;
    }

    public static DemoUserVO convert2Vo(DemoUser po) {
        DemoUserVO vo = new DemoUserVO();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }



}

