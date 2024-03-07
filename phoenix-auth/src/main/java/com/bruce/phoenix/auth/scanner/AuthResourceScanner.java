package com.bruce.phoenix.auth.scanner;

import cn.hutool.core.util.StrUtil;
import com.bruce.phoenix.auth.model.dto.ResourceDTO;
import com.bruce.phoenix.auth.model.enums.MethodEnum;
import com.bruce.phoenix.auth.model.enums.ResourceTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/7 10:24
 * @Author Bruce
 */
@Slf4j
@Component
public class AuthResourceScanner implements BeanPostProcessor, InitializingBean {

    private static List<ResourceDTO> menuList = new ArrayList<>();

    private static List<ResourceDTO> resourceList = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream in = AuthResourceScanner.class.getClassLoader().getResourceAsStream("menu.yaml");
            if (in == null) {
                log.info("skip scan menu because menu.yaml not exist");
                return;
            }
            Yaml yaml = new Yaml();
            LinkedHashMap<String, Object> map = yaml.load(in);
            processMenu((List) map.get("menu"), StringUtils.EMPTY);
        } catch (Exception e) {
            log.error("scan menu file fail,message={}", e.getMessage(), e);
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        try {
            Class clazz = null;
            if (AopUtils.isAopProxy(bean)) {
                clazz = AopProxyUtils.getSingletonTarget(bean).getClass();
            } else {
                clazz = bean.getClass();
            }
            // 没有@Controller,@RestController
            if (!clazz.isAnnotationPresent(Controller.class) && !clazz.isAnnotationPresent(RestController.class)) {
                return bean;
            }
            for (String rootPath : parseMappingAnnotation(clazz.getAnnotations())) {
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(AuthResource.class)) {
                        AuthResource res = method.getAnnotation(AuthResource.class);
                        for (String path : parseMappingAnnotation(method.getAnnotations())) {
                            if (StringUtils.isNotEmpty(path)) {
                                String mergePath = rootPath + path;
                                ResourceDTO resource =
                                        ResourceDTO.builder().name(res.name()).code(res.code()).parentCode(res.parentCode()).url(mergePath).method(res.method().getCode()).icon(StringUtils.EMPTY).orderNum(res.order()).type(ResourceTypeEnum.BUTTON.getCode()).version(res.version()).build();
                                resourceList.add(resource);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("scan resource fail,message={}", e.getMessage(), e);
        }
        return bean;
    }

    private void processMenu(List<LinkedHashMap<String, Object>> menus, String parentCode) {
        int order = 1;
        if (menus == null) {
            return;
        }
        for (LinkedHashMap<String, Object> menuMap : menus) {
            String name = (String) menuMap.get("name");
            String code = (String) menuMap.get("code");
            String path = (String) menuMap.get("path");
            String version = (String) menuMap.get("version");
            String icon = menuMap.get("icon") != null ? (String) menuMap.get("icon") : StringUtils.EMPTY;
            ResourceDTO resource =
                    ResourceDTO.builder().name(name).code(code).parentCode(parentCode).url(path).method(MethodEnum.GET.getCode()).orderNum(order).icon(icon).type(ResourceTypeEnum.MENU.getCode()).version(version).build();
            menuList.add(resource);
            if (menuMap.containsKey("menu")) {
                processMenu((List) menuMap.get("menu"), code);
            }
            order++;
        }
    }

    private List<String> parseMappingAnnotation(Annotation[] annotationList) {
        for (Annotation annotation : annotationList) {
            if (annotation instanceof RequestMapping) {
                return parseListValue(((RequestMapping) annotation).value());
            } else if (annotation instanceof GetMapping) {
                return parseListValue(((GetMapping) annotation).value());
            } else if (annotation instanceof PostMapping) {
                return parseListValue(((PostMapping) annotation).value());
            } else if (annotation instanceof DeleteMapping) {
                return parseListValue(((DeleteMapping) annotation).value());
            } else if (annotation instanceof PatchMapping) {
                return parseListValue(((PatchMapping) annotation).value());
            } else if (annotation instanceof PutMapping) {
                return parseListValue(((PutMapping) annotation).value());
            }
        }
        return Arrays.asList(StringUtils.EMPTY);
    }


    private List<String> parseListValue(String[]... pathList) {
        Set<String> resultSet = new LinkedHashSet<>();
        for (String[] paths : pathList) {
            for (String path : paths) {
                resultSet.add(path.replaceAll("\\{.*\\}", "*"));
            }
        }
        return new ArrayList<>(resultSet);
    }


    public static List<ResourceDTO> getMenuList(String version) {
        if (StrUtil.isNotBlank(version)) {
            List<ResourceDTO> versionMenuList =
                    menuList.stream().filter(resourceDTO -> version.equals(resourceDTO.getVersion())).collect(Collectors.toList());
            return Collections.unmodifiableList(versionMenuList);
        }
        return Collections.unmodifiableList(menuList);
    }

    public static List<ResourceDTO> getResourceList(String version) {
        if (StrUtil.isNotBlank(version)) {
            List<ResourceDTO> versionResourceList =
                    resourceList.stream().filter(resourceDTO -> version.equals(resourceDTO.getVersion())).collect(Collectors.toList());
            return Collections.unmodifiableList(versionResourceList);
        }

        return Collections.unmodifiableList(resourceList);
    }
}
