## 工程名称

    phoenix 意为凤凰，有种涅槃重生的感觉

## 安装与使用

### 安装

    依次安装 phoneix-common , phoenix-mp-generator-maven-plugin ,  phoneix-core , phoneix-sys（可选）
    安装到本地:  mvn install
    也可以安装到自己的私服 mvn deploy

### 使用方式

    <dependencies>
        <dependency>
            <groupId>com.bruce</groupId>
            <artifactId>phoenix-core</artifactId>
            <version>${phoenix.version}</version>
        </dependency>
        <dependency>
            <groupId>com.bruce</groupId>
            <artifactId>phoenix-sys</artifactId>
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

### 执行脚本

```shell
phoneix-mp-generator:generate
```

## 模块介绍

```text
- phoenix-common
    -   封装了常用的java工具，尽可能不依赖于第三方的包
- phoenix-core
    -   封装了核心 java 组件，整合一些常用工具，比如 redis 等
- phoenix-mp-generator-maven-plugin
    -   mp 代码自动生成插件，以插件形式打包，完成后端代码基础的 crud 工作
- phoenix-demo-web
    -   示例工程，引入两个 jar 包，完成简单的项目工程构建,还有组件的使用方法的演示    
```
### phoenix-common 模块

#### http 请求封装 BaseHttpUtil

```text
基于 hutool 的 HttpUtil 的进一步封装，会记录日志
详见 HttpUtilConfig
```

#### 数据校验注解

```text
com.bruce.phoenix.common.annotation 包下封装了注解，用于完成对请求参数的校验
```

#### 实体转化工具 BaseConverter

```text
完成 vo form po 实体类的转换
```

### phoenix-core 模块

- [x] mp generator
- [x] aop 日志
- [x] 异常拦截
- [x] nacos
- [ ] elk
- [ ] rabbit
- [x] redis
- [ ] mongo
- [x] easyexcel
- [x] springboot security
- [x] websocket
- [ ] dubbo
- [x] limiter限流
- [x] 线程和定时任务异常处理
- [x] 事件发布模型
- [x] 基于redis的消息队列

#### Limiter 限流工具类

限流注解，在 expire 时间内运行访问 count 次
```text
@GetMapping("/limiter")
@Limiter(key = "limiter:", message = "正在处理中，请勿重复操作!", expire = 60000, count = 1)
public Result<String> limiter() {
    ThreadUtil.sleep(10000);
    return Result.success();
}
```

- key 自定义前缀，后面会拼上入参
- message 限流时会抛出异常的内容
- expire  时间窗口大小 单位 毫秒
- count   时间窗口内允许通过的次数

#### EventModel 事件发布模型

```text
EventModel<String> model = new EventModel<>();
model.setParams("world");
model.setEventService((params) -> {
    log.info("hello {}", params);
});
eventComponent.publishEvent(model);
```

- params    事件参数，泛型类型
- eventService   采用函数式接口的方式，事件发布的回调

#### MqModel 基于redis的消息队列

```text
MqModel<String> model = new MqModel<>();
model.setParams("world");
model.setTopic(DemoConstant.TOPIC_NAME_DEMO);
model.setType(DemoConstant.MQ_TYPE_NAME_DEMO);
mqComponent.sendMessage(model);
```

- params 消息参数，泛型类型
- topic  消息通道的 topic
- type   消息处理服务的类型，采用策略模式
- errorAllowCount 允许重试次数

### phoenix-sys 模块(可选)

- [x] 数据字典模块
- [x] 系统设置模块
- [x] 系统日志模块
- [x] api日志模块

#### api 日志模块

```text
对 hutool 的 HttpUtil 做的 aop 拦截，会将请求和返回内容记录到表里
详见 LogHttpUtilConfig
```


