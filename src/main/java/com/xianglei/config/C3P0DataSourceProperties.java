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
    private String driver;
    private String jdbcUrl;
    private String username;
    private String password;
    private int minPoolSize;
    private long maxIdleTime;
    private int maxPoolSize;
    private int acquireIncrement;
    private int maxStatements;
    private int initialPoolSize;
    private int idleConnectionTestPeriod;
    private int acquireRetryAttempts;
    private int acquireRetryDelay;
    private boolean breakAfterAcquireFailure;
    private boolean testConnectionOnCheckout;

}
