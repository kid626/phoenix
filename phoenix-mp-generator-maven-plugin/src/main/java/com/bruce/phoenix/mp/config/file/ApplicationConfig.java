package com.bruce.phoenix.mp.config.file;

import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;

import java.io.File;

/**
 * @Copyright Copyright © 2023 fanzh . All rights reserved.
 * @Desc
 * @ProjectName mybatis-plus-maven-plugin
 * @Date 2023/11/29 20:47
 * @Author Bruce
 */
public class ApplicationConfig extends FileOutConfig {

    private String path;

    public ApplicationConfig(String templatePath, String path) {
        super(templatePath);
        this.path = path;
    }

    @Override
    public String outputFile(TableInfo tableInfo) {
        return path + File.separator + "Application.java";
    }


}
