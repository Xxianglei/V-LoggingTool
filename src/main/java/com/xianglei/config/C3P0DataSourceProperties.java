package com.xianglei.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @auther: Xianglei
 * @Company:
 * @Date: 2020/6/17 15:07
 * com.xianglei.config
 * @Description:
 */
@ConfigurationProperties(prefix = "c3p0")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class C3P0DataSourceProperties {
    /**
     * 默认设置如下配置项并提供默认值
     */
    private String driver = "com.mysql.jdbc.Driver";
    private String jdbcUrl = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&serverTimezone=Hongkong";
    private String username = "root";
    private String password = "root";
    private int minPoolSize = 2;
    private int initialPoolSize = 10;
    private int maxPoolSize = 50;
    private int acquireIncrement = 5;
    private int maxIdleTime = 1800000;
    private int maxStatements = 1000;
    private int idleConnectionTestPeriod = 60;
    private int acquireRetryAttempts = 30;
    private int acquireRetryDelay = 1000;
    private boolean breakAfterAcquireFailure = false;
    private boolean testConnectionOnCheckout = false;

}
