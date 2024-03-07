package com.bruce.phoenix.auth.controller;

import cn.hutool.core.io.IoUtil;
import com.bruce.phoenix.auth.component.AuthComponent;
import com.bruce.phoenix.auth.component.TokenComponent;
import com.bruce.phoenix.auth.config.AuthProperty;
import com.bruce.phoenix.auth.filter.CustomSecurityMetadataSource;
import com.bruce.phoenix.auth.model.common.ImageCaptcha;
import com.bruce.phoenix.auth.model.dto.CaptchaDTO;
import com.bruce.phoenix.auth.model.dto.LoginDTO;
import com.bruce.phoenix.auth.model.dto.ResourceDTO;
import com.bruce.phoenix.auth.model.vo.ResourceVO;
import com.bruce.phoenix.auth.scanner.AuthResourceScanner;
import com.bruce.phoenix.auth.scanner.AuthResourceUtil;
import com.bruce.phoenix.auth.service.AuthService;
import com.bruce.phoenix.auth.util.CookieUtil;
import com.bruce.phoenix.common.exception.CommonException;
import com.bruce.phoenix.common.model.common.Result;
import com.bruce.phoenix.core.model.security.UserAuthentication;
import com.bruce.phoenix.core.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/6 19:00
 * @Author Bruce
 */
@RestController
@RequestMapping("/auth")
@Slf4j
@Api(tags = "权限相关")
public class AuthController {

    @Resource
    private AuthService authService;
    @Resource
    private AuthProperty property;
    @Resource
    private TokenComponent tokenComponent;
    @Resource
    private AuthComponent authComponent;
    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;
    @Resource
    private CustomSecurityMetadataSource customSecurityMetadataSource;


    @ApiOperation("登录")
    @PostMapping(value = "/login")
    public Result<UserAuthentication> login(@RequestBody LoginDTO dto) {
        String username = dto.getUsername();
        String password = authComponent.getRealPassword(username, dto.getPassword());
        dto.setPassword(password);
        UserAuthentication user = authService.login(dto);
        //cookie
        response.addCookie(CookieUtil.createCookie(property.getToken().getName(), user.getToken()));
        return Result.success(user);
    }

    @ApiOperation("单点登录跳转")
    @GetMapping(value = "/redirect")
    public void redirect(String homePage) {
        try {
            String token = tokenComponent.getToken(request);
            response.addCookie(CookieUtil.createCookie(property.getToken().getName(), token));
            response.sendRedirect(homePage);
        } catch (IOException e) {
            log.error("重定向失败:{}", e.getMessage(), e);
        }
    }

    @ApiOperation("全局登出")
    @GetMapping(value = "/logout")
    public Result<String> logout() {
        authService.logout();
        return Result.success();
    }

    @ApiOperation("获取登录用户信息")
    @GetMapping(value = {"/info", "user/info"})
    public Result<UserAuthentication> info() {
        UserAuthentication user = UserUtil.getCurrentUser();
        return Result.success(user);
    }

    @ApiOperation("用户资源权限")
    @GetMapping(value = "/perm/list")
    public Result<List<String>> permList() {
        List<String> list = authService.permList();
        return Result.success(list);
    }

    @ApiOperation("获取登录密钥")
    @GetMapping(value = "/secretKey")
    public Result<String> loginSecretKey(String username) {
        String secretKey = authComponent.getLoginSecretKey(username);
        return Result.success(secretKey);
    }

    @ApiOperation("获取菜单树")
    @GetMapping(value = "/menuTree")
    public Result<List<ResourceVO>> menuTree() {
        List<ResourceVO> list = authService.tree();
        return Result.success(list);
    }

    @ApiOperation("获取图形验证码")
    @GetMapping(value = "/images/captcha")
    public void getImageCaptcha(@Validated CaptchaDTO dto) {
        try {
            AuthProperty.CaptchaManager captcha = property.getCaptcha();
            if (captcha == null || !Boolean.TRUE.equals(captcha.getEnable())) {
                return;
            }
            if (dto.getRid().length() != captcha.getLength()) {
                throw new CommonException("invalid rid");
            }
            ImageCaptcha imageCaptcha = authComponent.createCaptcha(dto.getWidth(), dto.getHeight(), dto.getRid());
            response.setHeader(captcha.getName(), imageCaptcha.getRid());
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            IoUtil.write(response.getOutputStream(), false, imageCaptcha.getLineCaptcha().getImageBytes());
        } catch (Exception e) {
            log.info("获取图形验证码失败:{}", e.getMessage(), e);
        }
    }


    @GetMapping(value = "/showResScript", produces = "text/html;charset=utf-8")
    @ApiOperation("资源生成脚本")
    public String showResScript(@RequestParam(defaultValue = "false") boolean autoIncrement,
                                @RequestParam(defaultValue = "1.0.0") String version) {
        if (property.isActiveProfile("prod")) {
            return StringUtils.EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("# 菜单初始化脚本\n");
        List<ResourceDTO> menuList = AuthResourceScanner.getMenuList(version);
        for (ResourceDTO res : menuList) {
            sb.append(AuthResourceUtil.genDDL(res, autoIncrement)).append("\n");
        }
        sb.append("# 操作初始化脚本\n");
        List<ResourceDTO> resourceList = AuthResourceScanner.getResourceList(version);
        for (ResourceDTO res : resourceList) {
            sb.append(AuthResourceUtil.genDDL(res, autoIncrement)).append("\n");
        }
        return sb.toString();
    }

    @GetMapping(value = "/refresh")
    @ApiOperation("刷新权限")
    public void refresh() {
        customSecurityMetadataSource.refreshResources();
    }


}
