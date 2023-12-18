package com.bruce.phoenix.mp;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.bruce.phoenix.mp.config.MpConfig;
import com.bruce.phoenix.mp.config.MyBatisPlusGenerator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName mybatis-plus-maven-plugin
 * @Date 2021/2/13 20:30
 * @Author Bruce
 */
@Mojo(name = "generate", threadSafe = true)
public class MpGenerateMojo extends AbstractMojo {

    private static final String DEFAULT_PATH = "mp-generator-config.yml";

    @Parameter
    private String configurationFile;

    /**
     * 日志工具
     */
    protected Log log = getLog();

    @Override
    public void execute() {
        log.info("==========================准备生成文件!!!==========================");
        InputStream inputStream = null;
        try {
            if (StrUtil.isNotEmpty(configurationFile)) {
                inputStream = new FileInputStream(configurationFile);
            } else {
                inputStream = MpGenerateMojo.class.getClassLoader().getResourceAsStream(DEFAULT_PATH);
            }
            MpConfig config = yaml2Config(inputStream);
            AutoGenerator mpg = configureAutoGenerator(config);
            mpg.execute();
            log.info("==========================文件生成完成!!!==========================");
        } catch (FileNotFoundException e) {
            log.error("文件生成失败:" + e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("文件流关闭失败:" + e.getMessage());
                }
            }
        }

    }


    private MpConfig yaml2Config(InputStream inputStream) {
        Yaml yaml = new Yaml();
        yaml.setBeanAccess(BeanAccess.FIELD);
        return yaml.loadAs(inputStream, MpConfig.class);
    }

    private AutoGenerator configureAutoGenerator(MpConfig config) {
        MyBatisPlusGenerator generator = new MyBatisPlusGenerator(config);
        AutoGenerator mpg = generator.autoGenerator();
        if (config.isUsevm()) {
            mpg.setTemplateEngine(new VelocityTemplateEngine());
        } else if (config.isUsebtl()) {
            mpg.setTemplateEngine(new BeetlTemplateEngine());
        } else {
            // 默认使用Freemarker
            mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        }
        return mpg;
    }

}
