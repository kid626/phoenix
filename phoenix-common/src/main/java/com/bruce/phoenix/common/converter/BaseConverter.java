package com.bruce.phoenix.common.converter;

import org.springframework.beans.BeanUtils;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc
 * @ProjectName demo
 * @Date 2022/6/30 14:41
 * @Author fzh
 */
public abstract class BaseConverter<F, P, V> {

    public void convert2Po(F form, P po) {
        BeanUtils.copyProperties(form, po);
        this.doConvert2Po(form, po);
    }

    public void convert2Vo(P po, V vo) {
        BeanUtils.copyProperties(po, vo);
        this.doConvert2Vo(po, vo);
    }

    protected abstract void doConvert2Po(F form, P po);

    protected abstract void doConvert2Vo(P po, V vo);

}
