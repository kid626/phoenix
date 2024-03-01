package com.bruce.phoenix.sys.model.converter;

import com.bruce.phoenix.sys.model.po.SysApiLog;
import com.bruce.phoenix.sys.model.form.SysApiLogForm;
import com.bruce.phoenix.sys.model.vo.SysApiLogVO;
import org.springframework.beans.BeanUtils;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc 系统第三方请求日志转换层
 * @ProjectName phoenix-sys
 * @Date 2024-03-01
 * @Author Bruce
 */
public class SysApiLogConverter {

    public static SysApiLog convert2Po(SysApiLogForm form) {
        SysApiLog po = new SysApiLog();
        BeanUtils.copyProperties(form, po);
        return po;
    }

    public static SysApiLogVO convert2Vo(SysApiLog po) {
        SysApiLogVO vo = new SysApiLogVO();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }



}

