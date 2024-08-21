package com.bruce.phoenix.common.util;

import cn.hutool.core.collection.CollUtil;
import com.bruce.phoenix.common.model.common.BaseSelectVO;
import com.bruce.phoenix.common.model.constants.CommonConstant;
import com.bruce.phoenix.common.model.enums.BaseServiceEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/8/21 9:28
 * @Author Bruce
 */
@Slf4j
public class EnumUtil {

    // 实现BaseServiceEnum接口的枚举类的Class Name做key，枚举类的values()做value
    private static Map<String, List<BaseSelectVO>> enumDict = new HashMap<>();

    /**
     * 生成枚举操作相关使用的缓存，减少频繁反射调用enum.values()并遍历比较所带来的性能消耗
     * 新扫表到的枚举类将会加入到缓存中，原缓存中存在的枚举类依旧存在，新旧枚举类相同时旧缓存会被覆盖
     *
     * @param basePackages 扫描包
     */
    public synchronized static <T extends BaseServiceEnum> void generateCache(String... basePackages) {
        if (basePackages == null) {
            return;
        }
        Set<String> packages = Arrays.stream(basePackages).filter(StringUtils::isNotBlank).collect(Collectors.toSet());
        Set<Class> enumClasses = new HashSet<>();

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
        for (String p : packages) {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(p)) + "/**/*Enum.class";

            try {
                Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
                for (Resource resource : resources) {
                    if (!resource.isReadable()) {
                        continue;
                    }
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    String className = metadataReader.getClassMetadata().getClassName();
                    Class<?> clazz = null;
                    try {
                        clazz = Class.forName(className);
                    } catch (Throwable e) {
                        log.warn(e.getMessage(), e);
                    }
                    // 检查是否为实现了ServiceEnum接口的枚举类
                    if (clazz != null && EnumUtil.isServiceEnum(clazz)) {
                        enumClasses.add(clazz);
                    }
                }
            } catch (Throwable e) {
                log.error("Enum 缓存构建失败，" + e.getMessage(), e);
            }


        }
        //构建枚举工具类的枚举类信息缓存
        EnumUtil.generateCache(enumClasses);
    }


    /**
     * 生成枚举操作相关使用的缓存，减少频繁反射调用enum.values()并遍历比较所带来的性能消耗
     * 新扫表到的枚举类将会加入到缓存中，原缓存中存在的枚举类依旧存在，新旧枚举类相同时旧缓存会被覆盖
     *
     * @param classes 枚举类集合
     */
    public synchronized static <T extends BaseServiceEnum> void generateCache(Set<Class> classes) {

        if (classes == null || classes.isEmpty()) {
            return;
        }
        classes.removeIf(cl -> !isServiceEnum(cl));

        Map<String, List<BaseSelectVO>> esMap = new HashMap<>();
        for (Class<T> clazz : classes) {
            if (clazz == null) {
                continue;
            }
            try {
                Method values = clazz.getMethod("values");
                T[] items = (T[]) values.invoke(null, null);
                if (items == null || items.length <= 0) {
                    continue;
                }
                List<BaseSelectVO> enumItems = new ArrayList<>(items.length);
                for (T item : items) {
                    enumItems.add(new BaseSelectVO(item.getCode(), item.getName()));
                }
                esMap.put(clazz.getName(), enumItems);
            } catch (Exception e) {
                // do nothing
            }
        }

        esMap.putAll(enumDict);

        EnumUtil.enumDict = Collections.unmodifiableMap(esMap);

        log.info("EnumUtil 枚举缓存已重新构建");
    }

    /**
     * 通过枚举类获取下拉框
     *
     * @param clazz 枚举类
     * @return 下拉框
     */
    public static <T extends BaseServiceEnum> List<BaseSelectVO> getSelect(Class<T> clazz) {
        if (clazz == null) {
            return new ArrayList<>();
        }
        List<BaseSelectVO> enumItems = enumDict.get(clazz.getName());
        if (enumItems != null) {
            return enumItems;
        }
        try {
            T[] items = clazz.getEnumConstants();
            enumItems = new ArrayList<>();
            for (T item : items) {
                enumItems.add(new BaseSelectVO(item.getCode(), item.getName()));
            }
            enumDict.put(clazz.getName(), enumItems);
            return enumItems;
        } catch (Exception ex) {
            //失败返回数据为空的List即可
            return new ArrayList<>();
        }
    }

    /**
     * 通过枚举类获取下拉框 带有全部
     *
     * @param clazz 枚举类
     * @return 下拉框
     */
    public static <T extends BaseServiceEnum> List<BaseSelectVO> getSelectWithAll(Class<T> clazz) {
        List<BaseSelectVO> result = new ArrayList<>();
        result.add(new BaseSelectVO(CommonConstant.ALL, CommonConstant.ALL_STR));
        List<BaseSelectVO> list = getSelect(clazz);
        if (CollUtil.isNotEmpty(list)) {
            result.addAll(list);
        }
        return result;
    }

    /**
     * 是否是实现了ServiceEnum的枚举类
     *
     * @return true / false
     */
    private static boolean isServiceEnum(Class<?> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();
        if (clazz.isEnum() && interfaces != null && interfaces.length > 0) {
            for (Class<?> cl : interfaces) {
                if (cl.equals(BaseServiceEnum.class)) {
                    return true;
                }
            }
        }
        return false;
    }


}
