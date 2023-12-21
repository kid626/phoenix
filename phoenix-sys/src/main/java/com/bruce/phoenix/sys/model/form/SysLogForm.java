package com.bruce.phoenix.sys.model.form;

import lombok.Data;

import java.io.Serializable;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2023/12/20 11:02
 * @Author Bruce
 */
@Data
public class SysLogForm implements Serializable {

    private String module;

    private Integer businessType;

    private String method;

    private String requestMethod;

    private Integer operatorType;

    private String operUrl;

    private String operIp;

    private String operLocation;

    private String operParam;

    private String operResult;

    private Integer status;

    private String errorMsg;

    private String createUser;

}
