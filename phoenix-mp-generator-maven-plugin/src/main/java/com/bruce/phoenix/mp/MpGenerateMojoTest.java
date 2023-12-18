package com.bruce.phoenix.mp;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.bruce.phoenix.mp.config.MpConfig;
import com.bruce.phoenix.mp.config.MyBatisPlusGenerator;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName mybatis-plus-maven-plugin
 * @Date 2021/2/13 20:30
 * @Author Bruce
 */
public class MpGenerateMojoTest {

    private static final String DEFAULT_PATH = "mp-generator-config.yml";

    public static void main(String[] args) throws IOException {
        try (InputStream inputStream = MpGenerateMojo.class.getClassLoader().getResourceAsStream(DEFAULT_PATH)) {
            MpConfig config = yaml2Config(inputStream);
            AutoGenerator mpg = configureAutoGenerator(config);
            mpg.execute();
        }
    }


    private static MpConfig yaml2Config(InputStream inputStream) {
        Yaml yaml = new Yaml();
        yaml.setBeanAccess(BeanAccess.FIELD);
        return yaml.loadAs(inputStream, MpConfig.class);
    }

    private static AutoGenerator configureAutoGenerator(MpConfig config) {
        MyBatisPlusGenerator generator = new MyBatisPlusGenerator(config);
        AutoGenerator mpg = generator.autoGenerator();
        if (config.isUsevm()) {
            mpg.setTemplateEngine(new VelocityTemplateEngine());
        } else if (config.isUsebtl()) {
            mpg.setTemplateEngine(new BeetlTemplateEngine());
        } else {
            //默认使用Freemarker
            mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        }
        return mpg;
    }

}
