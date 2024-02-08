package com.bruce.phoenix.sys.model.converter;

import com.bruce.phoenix.common.converter.BaseConverter;
import com.bruce.phoenix.common.model.enums.BusinessStatusEnum;
import com.bruce.phoenix.common.model.enums.BusinessTypeEnum;
import com.bruce.phoenix.common.model.enums.OperatorTypeEnum;
import com.bruce.phoenix.sys.model.po.SysLog;
import com.bruce.phoenix.sys.model.vo.SysLogVO;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc
 * @ProjectName demo
 * @Date 2022/6/30 14:41
 * @Author fzh
 */
public class SysLogConverter extends BaseConverter<SysLog, SysLog, SysLogVO> {

    @Override
    protected void doConvert2Po(SysLog form, SysLog po) {

    }

    @Override
    protected void doConvert2Vo(SysLog po, SysLogVO vo) {
        vo.setBusinessTypeStr(BusinessTypeEnum.LOOKUP.get(po.getBusinessType()));
        vo.setOperatorTypeStr(OperatorTypeEnum.LOOKUP.get(po.getOperatorType()));
        vo.setStatusStr(BusinessStatusEnum.LOOKUP.get(po.getStatus()));
    }


}
