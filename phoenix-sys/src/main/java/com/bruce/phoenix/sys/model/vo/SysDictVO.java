package com.bruce.phoenix.sys.model.vo;

import cn.hutool.core.collection.CollUtil;
import com.bruce.phoenix.common.model.enums.YesOrNoEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2023/12/20 13:44
 * @Author Bruce
 */
@Data
public class SysDictVO {


    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "父主键")
    private Long pId;

    @ApiModelProperty(value = "字典编码")
    private String dictCode;

    @ApiModelProperty(value = "父编码")
    private String pCode;

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "字典值")
    private String dictValue;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;

    @ApiModelProperty(value = "是否启用")
    private String isEnable;

    @ApiModelProperty(value = "是否有子节点")
    private String hasChild;

    @ApiModelProperty(value = "子节点")
    private List<SysDictVO> children = new ArrayList<>();

    public void add(SysDictVO node) {
        children.add(node);
    }

    public void hasChild() {
        hasChild = CollUtil.isEmpty(children) ? YesOrNoEnum.NO.getCode() : YesOrNoEnum.YES.getCode();
    }
}
