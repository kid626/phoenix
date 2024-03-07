package com.bruce.phoenix.auth.model.vo;

import com.bruce.phoenix.core.model.security.AuthResource;
import lombok.Data;

import java.util.List;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName security
 * @Date 2021/12/26 16:49
 * @Author fzh
 */
@Data
public class ResourceVO extends AuthResource {

    private List<ResourceVO> children;

    private boolean hasChild;

}
