package com.bruce.phoenix.auth.model.common;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.Date;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName security
 * @Date 2021/12/27 13:45
 * @Author fzh
 */
@Data
public class ImageCaptcha {

    private String rid;
    private String code;
    private Date createdAt;
    private LineCaptcha lineCaptcha;

    public static ImageCaptcha create(int width, int height, int codeCount, int lineCount, String rid) {
        if (StrUtil.isBlank(rid)) {
            rid = RandomUtil.randomString(16);
        }
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(width, height, codeCount, lineCount);
        ImageCaptcha ret = new ImageCaptcha();
        ret.setRid(rid);
        ret.setCode(lineCaptcha.getCode());
        ret.setLineCaptcha(lineCaptcha);
        ret.setCreatedAt(DateUtil.date());
        return ret;
    }


}
