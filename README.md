# 名称

phoenix

### 安装

    依次安装 phoneix-common , phoenix-mp-generator-maven-plugin ,  phoneix-core

    安装到本地:  mvn install
    也可以安装到自己的私服 mvn deploy


### 使用方式

    <dependencies>
        <dependency>
            <groupId>com.bruce</groupId>
            <artifactId>phoenix-core</artifactId>
            <version>${phoenix.version}</version>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>2.2.3.RELEASE</version>
            </plugin>
            <plugin>
                <groupId>com.bruce</groupId>
                <version>${phoenix.version}</version>
                <artifactId>phoenix-mp-generator-maven-plugin</artifactId>
                <configuration>
                    <configurationFile>src/main/resources/mp-generator-config.yml</configurationFile>
                </configuration>
            </plugin>
        </plugins>
    </build>
    
### 配置文件
        src/main/resources/mp-generator-config.yml
```
    # 作者
    author: Bruce
    # 数据库相关配置
    dbType: mysql
    dbUrl: "jdbc:mysql://localhost:3306/mybatis_plus?characterEncoding=utf8&serverTimezone=GMT%2B8"
    dbDriverName: "com.mysql.cj.jdbc.Driver"
    dbUsername: root
    dbPassword: root
    
    # 父文件夹
    parentPackage: com.bruce.mp.demo
    # 项目名称
    projectName: mp-demo
    # 多模块模块名，单模块可以为空
    moduleName: mybatis-plus-generator-plugin
    # 接口版本号
    version: v1
    # 是否初始化
    isFirstInit: false
    # 表前缀
    #prefix: demo
    # 包含数据库表列表
    includes: [ "demo_user" ]
    # 排除数据库表列表
    excludes: [ ]
    
    # 自定义输出文件位置，默认为 $("user.dir")/{moduleName}/src/main/resources;
    #outputDir:
    
    # 使用模板引擎，默认使用 FreemarkerTemplateEngine
    #usebtl: true
    usevm: true
    #useftl: true
    
    # 自定义模板
    custom: true
```
