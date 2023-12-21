package com.bruce.phoenix.common.tree;


import com.bruce.phoenix.common.model.form.TreeNodeForm;
import com.bruce.phoenix.common.model.po.TreeNode;
import com.bruce.phoenix.common.model.vo.TreeNodeVo;

/**
 * @Copyright Copyright © 2022 fanzh . All rights reserved.
 * @Desc
 * @ProjectName tree-generator
 * @Date 2022/3/1 15:59
 * @Author fzh
 */
public interface TreeNodeConvertService<T extends TreeNode, F extends TreeNodeForm, V extends TreeNodeVo<V>> {

    V convert2Vo(T tree);

    T convert2Po(F form);


}
