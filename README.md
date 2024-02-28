### 工程名称

    phoenix 意为凤凰，有种涅槃重生的感觉

### 模块介绍

```text
- phoenix-common
    -   封装了常用的java工具，尽可能不依赖于第三方的包
- phoenix-core
    -   封装了核心 java 组件，整合一些常用工具，比如 redis 等
- phoenix-mp-generator-maven-plugin
    -   mp 代码自动生成插件，以插件形式打包，完成后端代码基础的 crud 工作
- phoenix-demo-web
    -   示例工程，引入两个 jar 包，完成简单的项目工程构建    
```
### phoenix-common 模块



### phoenix-core 模块

- [x] mp generator
- [x] aop 日志
- [x] 异常拦截
- [ ] springboot admin
- [x] nacos
- [ ] elk
- [ ] rabbit
- [x] redis
- [ ] mongo
- [x] easyexcel
- [ ] springboot security
- [x] websocket
- [ ] dubbo
- [x] limiter限流
- [x] 线程和定时任务异常处理


### 安装与使用

#### 安装

    依次安装 phoneix-common , phoenix-mp-generator-maven-plugin ,  phoneix-core

    安装到本地:  mvn install
    也可以安装到自己的私服 mvn deploy


#### 使用方式

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
    
#### 配置文件
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
