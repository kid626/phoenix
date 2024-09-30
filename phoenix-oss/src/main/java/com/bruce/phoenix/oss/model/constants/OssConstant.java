package com.bruce.phoenix.oss.model.constants;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/9/29 14:10
 * @Author Bruce
 */
public final class OssConstant {
    private OssConstant() {}

    public static final String PREFIX = "oss";
    public static final String FILE_INFO_CACHE = PREFIX + "info:{0}";

    /**
     * 文件上传token名
     * 因为nginx建议全部小写
     */
    public static final String UPLOAD_TOKEN = "uploadtoken";

}
