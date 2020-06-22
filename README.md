### V-LoggingTool

> 目前只是一个Demo，还不是一个可以依赖的类库，最近会去逐渐实现并总结如何写一个类库。

该项目是针对开发中的一个日志业务模块的一个简单实现。其基于Aop实现使用SpringBoot自动配置功能来实现开箱即用的目标。
<br>该类库目的是对用户访问操作进行日志记录/分发。
<br>借此打算就抽离出业务做一个类库开发的案例。

### 目标

- Mysql存储
- 消息中间件
- 本地文件存储

### 设计思想

- 思想：给予用户配置权限
- 思想：默认配置
- 思想：声明大于配置
- 思想：多态分发

### 已实现

@VLogHunter(logType = AuditLogTypeEnum.ACCOUNT_MANAGE, description = "日志列表查看操作", name = "日志列表查看")<br>

可使用@VLogHunter注解在Controller或全局异常处理方法中，进行用户访问访问日志的生成和存储。

- 目前以支持Mysql数据库
- 目前支持HikariCP连接池
- 目前支持C3P0连接池
- 目前支持本地文件存储
- 目前支持日志发送MQ

总体设计目标正在实现中，后期提供jar依赖和详细的使用说明。

### 使用说明

#### Maven
```xml

```
#### 配置说明
> 由于SpringBoot2.0 默认支持HikariCP，DHCP等，本项目选择以C3P0连接池做一个SpringBoot自动装配的开发案例。
- C3P0连接池

根据自己所需选择对应配置即可。

![](https://static01.imgkr.com/temp/9210e0534b4e44208f7e69aff55d2682.png)

- 动态配置

```yaml
v-log:
  file:
# 是否开启本地文件存储
    file-open: true
# 是否发送消息到RocketMQ
    mq-open: true
# 文件格式（文件命名规则）
    name-format: yyyyMMddHHmmss
# 文件格式（仅支持文本文件）
    ext-name: .txt
# 日志文件存储路径（支持linux，windows）
    store-file-path: D:\\logs
  thread:
# 核心线程数
    core-pool-size: 1
# 最大线程数
    max-pool-size: 5.
# 线程池队列大小
    queue-capacity: 10 
```
如果开启了mq-open: true,你需要配置MQ的相关配置项目如下：
```yaml
rocketmq:
  name-server: 120.26.184.125:9876
  producer:
    group: log-group
# 可自定义配置
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
        <spring-boot.version>2.2.1.RELEASE</spring-boot.version>
        <mysql.version>8.0.16</mysql.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <!--mysql-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
            <version>${mysql.version}</version>
        </dependency>
        <dependency>
            <groupId>com.github</groupId>
            <artifactId>log-capture-tool</artifactId>
            <version>1.0.0</version>
        </dependency>
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.3</version>
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
本依赖中已经继承了线程池，你如果需要开启异步处理，只需在主类注入 **@EnableAsync**
- 日志捕捉注解使用

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
其他参数暂时还没作为持久化的必要数据。
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
### 注意
- 项目中采用的Mysql版本是8.0以上，请注意版本兼容。