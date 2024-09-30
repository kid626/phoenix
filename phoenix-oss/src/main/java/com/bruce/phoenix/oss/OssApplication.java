package com.bruce.phoenix.oss;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Copyright Copyright © 2024 Bruce . All rights reserved.
 * @Desc Controller 接口
 * @ProjectName phoenix-oss
 * @Date 2024-09-29
 * @Author Bruce
 */
@SpringBootApplication(scanBasePackages = {"com.bruce.phoenix.common", "com.bruce.phoenix.core", "com.bruce.phoenix.sys", "com.bruce.phoenix.oss"})
@MapperScan(basePackages = {"com.bruce.phoenix.sys.mapper", "com.bruce.phoenix.oss.mapper"})
@EnableScheduling
@EnableCaching
@Slf4j
public class OssApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(OssApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        if (path == null) {
            path = "";
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Application  is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "Doc: \t\thttp://localhost:" + port + path + "/doc.html\n" +
                "----------------------------------------------------------");
        log.info("\n (♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }

}
