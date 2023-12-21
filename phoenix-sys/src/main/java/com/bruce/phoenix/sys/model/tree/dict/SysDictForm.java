package com.bruce.phoenix.sys.model.tree.dict;

import com.bruce.phoenix.common.model.form.TreeNodeForm;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2023/12/20 13:44
 * @Author Bruce
 */
@Data
public class SysDictForm extends TreeNodeForm {

    @ApiModelProperty(value = "字典值")
    private String dictValue;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;

    @ApiModelProperty(value = "是否启用")
    private String isEnable;

}
