package com.bruce.phoenix.common.model.vo;

import cn.hutool.core.collection.CollUtil;
import com.bruce.phoenix.common.model.enums.YesOrNoEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName tree_generator
 * @Date 2021/2/4 21:44
 * @Author Bruce
 */
@Data
public class TreeNodeVo<T> implements Serializable {

    protected Long id;
    protected Long pId;
    protected String hasChild;
    protected String name;
    protected String code;
    protected List<T> children = new ArrayList<>();

    public void add(T node) {
        children.add(node);
    }

    public void hasChild() {
        hasChild = CollUtil.isEmpty(children) ? YesOrNoEnum.NO.getCode() : YesOrNoEnum.YES.getCode();
    }

}
