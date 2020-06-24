### V-LoggingTool

V-LoggingTool 1.0版本，基于自动配置和AOP技术，实现低侵入的用户操作的拦截、存储、分发功能。欢迎 <em>**start / fork！**</em>


### 目的意义

该仓库目的主要意义是在于小白学习和实现小项目快速开发。

- 开箱即用，满足一般场景日志系统需求
- 提供基于SpringBoot自动配置手写starter案例
- 提供Aop在日志拦截系统场景中的使用案例
- 提供SpringBoot异步线程池使用案例
- 提供SpringBoot集成MyBatis-Plus使用案例

### 功能支持

- Mysql数据库存储 -- 支持异步
- 发送到消息中间件 -- RocketMQ
- 自定义本地文件存储 -- 24小时归档

### 设计思想

- 思想：给予用户配置权限
- 思想：默认配置
- 思想：声明大于配置
- 思想：多态分发

### 已实现

@VLogHunter(logType = AuditLogTypeEnum.ACCOUNT_MANAGE, description = "日志列表查看操作", name = "日志列表查看")<br>

可使用@VLogHunter注解在 **Controller** 或 **全局异常处理** 方法中，进行用户访问访问日志的生成和存储。

- 支持Mysql数据库
- 支持HikariCP连接池
- 支持C3P0连接池
- 支持本地文件存储
- 支持日志发送MQ

### 优点与不足

- 优点
  - 快
- 不足
  - 返回值必须是  CommonResponse \<T>
  - 需要在session中需要存储当前访问用户的userName属性，该值会作为入库的用户名，否则为null
    
对于已存在的不足点后期会**持续跟进和改善代码逻辑**，如果你目前无法接受就只能移步其他仓库。目前支持Mysql5.0及以上版本，请注意版本兼容性。

### 使用说明

#### Maven
```xml
  <dependency>
    <groupId>com.github</groupId>
    <artifactId>log-capture-tool</artifactId>
    <version>1.0.0</version>
  </dependency>

  <repositories>
        <repository>
            <id>github</id>
            <name>BB zebra</name>
            <url>https://raw.github.com/Xxianglei/maven-repository/master</url>
        </repository>
  </repositories>
```
如果拉取失败
- 请先删除本地仓库路径下的log-capture-tool
- 参照[解决GitHub的raw.githubusercontent.com无法连接问题](https://www.cnblogs.com/sinferwu/p/12726833.html) 处理hosts
- 再次拉取依赖
#### 配置说明
> 由于SpringBoot2.0 默认支持HikariCP，DHCP等，本项目选择以C3P0连接池做一个SpringBoot自动装配的开发案例。
- C3P0连接池

根据自己所需选择对应配置即可。

![](https://static01.imgkr.com/temp/9210e0534b4e44208f7e69aff55d2682.png)

- 全局动态配置

```yaml
v-log:
  file:
# 是否开启本地文件存储 默认 false
    fileOpen: true
# 是否发送消息到RocketMQ
    mqOpen: false
# 文件格式（文件命名规则）
    nameFormat: yyyyMMddHHmmss
# 文件格式（仅支持文本文件）
    extName: .txt
# 日志文件存储路径（支持linux，windows） 默认 C:\\logs 或 /usr/local
    storeFilePath: D:\\logs
  thread:
# 线程池配置
    corePoolSize: 1
# 最大线程数
    maxPoolSize: 5
# 线程池队列大小
    queueCapacity: 10
```
你还需要配置MQ的相关配置项目如下，如果开启了mq-open: true,日志消息将会发送到MQ
```yaml
rocketmq:
  name-server: 你的IP:9876
  producer:
    group: log-group
# 可自定义配置 默认 v-log-topic
    topic: xxx
```
### 使用案例

- pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.1.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.example</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>demo</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
        <mybatis-plus.version>3.3.2</mybatis-plus.version>
        <mysql.version>5.0.7</mysql.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.3</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
            <version>${mysql.version}</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <!--mybatis plus-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <optional>true</optional>
            <version>${mybatis-plus.version}</version>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-generator</artifactId>
            <optional>true</optional>
            <version>${mybatis-plus.version}</version>
        </dependency>
        <!--引入我的jar 默认集成C3P0连接池-->
        <dependency>
            <groupId>com.github</groupId>
            <artifactId>log-capture-tool</artifactId>
            <version>1.0.0</version>
        </dependency>
       <dependency>
            <groupId>org.apache.rocketmq</groupId>
            <artifactId>rocketmq-spring-boot-starter</artifactId>
            <version>2.0.3</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
    <repositories>
        <repository>
            <id>github</id>
            <name>BB zebra</name>
            <url>https://raw.github.com/Xxianglei/maven-repository/master</url>
        </repository>
    </repositories>
</project>
```
- 启动类设置包扫描
```java
// 依赖中Mapper路径
@MapperScan("com.xianglei.mapper")
// 后者是依赖中包路径
@ComponentScan(basePackages = {"com.example.demo", "com.xianglei"})
```
- 手动开启异步线程池
本依赖中已经集成了线程池，你如果需要开启异步处理，只需在主类注入 **@EnableAsync**
- 日志捕捉注解使用

返回给前端JSON格式数据，请默认使用CommonResponse封装，AOP会根据其Code值进行处理。

```java
    @RequestMapping("/test")
    @VLogHunter(name = "demo",module = VLogHunter.Module.CONTROLLER,method = VLogHunter.MethodType.GET,logType = AuditLogTypeEnum.COMMON_LOG,description = "测试操作")
    @ResponseBody
    public CommonResponse hello() {
        return new CommonResponse();
    }
```
必填项：
```java
 @VLogHunter(logType = AuditLogTypeEnum.COMMON_LOG, description = "测试操作")
```
其他参数暂时还没作为持久化的必要数据，如有需要可以自己二次封装，或者提出issue。

### SQL脚本
```sql
CREATE TABLE `audit_log` (
  `flow_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL,
  `login_ip` varchar(20) DEFAULT NULL,
  `log_type` varchar(32) DEFAULT NULL,
  `details` varchar(64) DEFAULT NULL,
  `operation_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`flow_id`)
) ;
```

#### Fixed 
- Mysql多版本支持
