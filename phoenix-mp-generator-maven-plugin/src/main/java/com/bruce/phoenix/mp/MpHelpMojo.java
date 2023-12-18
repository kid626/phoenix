package com.bruce.phoenix.mp;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName mybatis-plus-maven-plugin
 * @Date 2021/2/13 20:30
 * @Author Bruce
 */
@Mojo(name = "help", threadSafe = true)
public class MpHelpMojo extends AbstractMojo {

    private static final String DEMO_PATH = "mp-generator-config-demo.yml";

    /**
     * 日志工具
     */
    protected Log log = getLog();

    @Override
    public void execute() {
        log.info("==========================准备读取文件!!!==========================");
        InputStream inputStream = null;
        try {
            inputStream = MpHelpMojo.class.getClassLoader().getResourceAsStream(DEMO_PATH);
            byte[] flash = new byte[1024 * 10];
            int len = -1;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(flash)) != -1) {
                String str = new String(flash, 0, len, Charset.forName("utf-8"));
                sb.append(str);
            }
            log.info("==========================文件读取完成!!!==========================\n" + sb);
        } catch (Exception e) {
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

}
