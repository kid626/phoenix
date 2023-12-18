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
public class SqlMapperConfig extends FileOutConfig {

    private String path;

    public SqlMapperConfig(String templatePath, String path) {
        super(templatePath);
        this.path = path + File.separator + "sqlmap" + File.separator + "auto";
    }

    @Override
    public String outputFile(TableInfo tableInfo) {
        String entityName = tableInfo.getEntityName();
        return path + File.separator + entityName + "Mapper.xml";
    }


}
