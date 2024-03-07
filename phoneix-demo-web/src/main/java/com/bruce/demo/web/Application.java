package com.bruce.demo.web;

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
 * @Copyright Copyright © 2023 Bruce . All rights reserved.
 * @Desc Controller 接口
 * @ProjectName phoenix-demo
 * @Date 2023-12-19
 * @Author Bruce
 */
@SpringBootApplication(scanBasePackages = {"com.bruce.phoenix.common", "com.bruce.phoenix.core", "com.bruce.phoenix.sys", "com.bruce.phoenix.auth", "com.bruce.demo.web"})
@MapperScan(basePackages = {"com.bruce.phoenix.sys.mapper", "com.bruce.phoenix.auth.mapper", "com.bruce.demo.web.mapper"})
@EnableScheduling
@EnableCaching
@Slf4j
public class Application {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(Application.class, args);
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
