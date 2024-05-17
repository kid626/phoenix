package com.bruce.demo.web.model;

import com.bruce.demo.web.model.po.DemoUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/5/17 16:36
 * @Author Bruce
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TestParent extends DemoUser {

    private String extend;
}
