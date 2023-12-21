package com.bruce.phoenix.sys.model.tree.dict;

import com.bruce.phoenix.common.model.po.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2023/12/20 13:52
 * @Author Bruce
 */
@Data
public class SysDictTree extends TreeNode {

    @ApiModelProperty(value = "字典值")
    private String dictValue;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;

    @ApiModelProperty(value = "是否启用")
    private String isEnable;

}
