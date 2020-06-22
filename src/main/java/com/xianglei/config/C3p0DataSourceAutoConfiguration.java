package com.xianglei.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @auther: Xianglei
 * @Company:
 * @Date: 2020/6/17 15:07
 * com.xianglei.config
 * @Description:
 */
@Configuration
@ConditionalOnClass(DataSource.class)
@EnableConfigurationProperties(C3P0DataSourceProperties.class)
public class C3p0DataSourceAutoConfiguration {

    @Autowired
    C3P0DataSourceProperties dataSourceProperties;

    // TODO 提前连接池初始化时间
    @Bean("C3P0-Pool")
    @ConditionalOnMissingBean
    public DataSource dataSource() throws Exception {
        // 创建一个 c3p0 的连接池
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(dataSourceProperties.getDriver());
        dataSource.setJdbcUrl(dataSourceProperties.getJdbcUrl());
        dataSource.setUser(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        // 池配置
        dataSource.setAcquireIncrement(5);
        dataSource.setInitialPoolSize(20);
        dataSource.setMinPoolSize(2);
        dataSource.setMaxPoolSize(50);
        /**/
        dataSource.setMaxIdleTime(dataSourceProperties.getMaxIdleTime());
        dataSource.setMaxStatements(dataSourceProperties.getMaxStatements());
        dataSource.setIdleConnectionTestPeriod(dataSourceProperties.getIdleConnectionTestPeriod());
        dataSource.setAcquireRetryDelay(dataSourceProperties.getAcquireRetryDelay());
        dataSource.setAcquireRetryAttempts(dataSourceProperties.getAcquireRetryAttempts());
        dataSource.setBreakAfterAcquireFailure(dataSourceProperties.isBreakAfterAcquireFailure());
        dataSource.setTestConnectionOnCheckout(dataSourceProperties.isTestConnectionOnCheckout());
        return dataSource;
    }
}
