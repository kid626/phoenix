package com.bruce.demo.web.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
* @Copyright Copyright © 2023 Bruce . All rights reserved.
* @Desc 启动类
* @ProjectName phoenix-demo
* @Date 2023-12-19
* @Author Bruce
*/
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Value("${swagger.enable:false}")
    private Boolean enable;

    /**
    * 创建API应用
    * apiInfo() 增加API相关信息
    * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
    * 本例采用指定扫描的包路径来定义指定要建立API的目录。
    */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo("phoenix-demo", "phoenix-demo 相关接口", "v1"))
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.bruce.demo.web.controller"))
            .paths(PathSelectors.any())
            .build()
            .groupName("api")
            .pathMapping("/")
            .enable(enable);
    }

    private ApiInfo apiInfo(String title, String description, String version) {
        return new ApiInfoBuilder()
            .title(title)
            .description(description)
            .contact(new Contact("Bruce", "", ""))
            .version(version)
            .build();
    }

}
