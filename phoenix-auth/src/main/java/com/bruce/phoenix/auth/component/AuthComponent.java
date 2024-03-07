package com.bruce.phoenix.auth.component;

import cn.hutool.core.util.RandomUtil;
import com.bruce.phoenix.auth.config.AuthProperty;
import com.bruce.phoenix.auth.model.common.ImageCaptcha;
import com.bruce.phoenix.auth.model.constant.RedisConstant;
import com.bruce.phoenix.common.exception.CommonException;
import com.bruce.phoenix.common.util.AesEcbUtil;
import com.bruce.phoenix.core.component.middleware.RedisComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName security
 * @Date 2021/12/27 13:42
 * @Author fzh
 */
@Component
@Slf4j
public class AuthComponent {

    @Resource
    private RedisComponent redisComponent;
    @Resource
    private AuthProperty property;

    public ImageCaptcha createCaptcha(Integer width, Integer height, String rid) {
        ImageCaptcha imageCaptcha = ImageCaptcha.create(width, height, property.getCaptcha().getCodeCount(),
                property.getCaptcha().getLineCount(), rid);
        String key = MessageFormat.format(RedisConstant.IMAGE_CAPTCHA_RID, imageCaptcha.getRid());
        redisComponent.setExpire(key, imageCaptcha.getCode(), property.getCaptcha().getExpire(), TimeUnit.SECONDS);
        return imageCaptcha;
    }

    public void checkCaptchaAndDelete(String rid, String code) {
        String key = MessageFormat.format(RedisConstant.IMAGE_CAPTCHA_RID, rid);
        String captcha = redisComponent.getAndDelete(key);
        if (StringUtils.isBlank(captcha)) {
            throw new CommonException("验证码错误!");
        }
        if (!StringUtils.equalsIgnoreCase(captcha, code)) {
            throw new CommonException("验证码错误!");
        }
    }

    public String getRealPassword(String username, String password) {
        AuthProperty.RetryManager retryManager = property.getRetry();
        try {
            String key = MessageFormat.format(RedisConstant.SECRET_KEY, username);
            // 加密密钥不存在表示密码不合法
            if (!redisComponent.exists(key)) {
                throw new CommonException("密钥不存在!");
            }
            //获取之后要立马删除
            String secretKey = redisComponent.getAndDelete(key);
            return AesEcbUtil.aesDecrypt(password, secretKey);
        } catch (Exception e) {
            log.error("解密失败:{}", e.getMessage(), e);
            incrExpireAndThrow(MessageFormat.format(RedisConstant.LOGIN_RETRY_NUM, username), retryManager);
            return "";
        }
    }

    public String getLoginSecretKey(String username) {
        String token = RandomUtil.randomString(16);
        // 设置1分钟有效期
        String key = MessageFormat.format(RedisConstant.SECRET_KEY, username);
        redisComponent.setExpire(key, token, 1L, TimeUnit.MINUTES);
        return token;
    }

    public void incrExpireAndThrow(String key, AuthProperty.RetryManager retryManager) throws CommonException {
        long retryNum = redisComponent.incr(key, 1);
        if (retryNum >= retryManager.getNums()) {
            throw new CommonException("您的账号已被锁定，请稍后尝试");
        }
        redisComponent.expire(key, retryManager.getExpire(), TimeUnit.SECONDS);
        throw new CommonException(MessageFormat.format("账号密码错误,若连错{0}次，账号将被锁定,您还有{1}次机会", retryManager.getNums(),
                retryManager.getNums() - retryNum));
    }

}
