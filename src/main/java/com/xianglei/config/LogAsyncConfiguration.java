package com.xianglei.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @auther: Xianglei
 * @Company: venusGroup
 * @Date: 2020/6/22 09:42
 * com.xianglei.config
 * @Description:
 */
@Configuration
public class LogAsyncConfiguration implements AsyncConfigurer {
    @Autowired
    LogThreadConfigProperties logThreadConfigProperties;

    @Bean(name = "VLogThreadPool")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(logThreadConfigProperties.getCorePoolSize());
        executor.setMaxPoolSize(logThreadConfigProperties.getMaxPoolSize());
        executor.setQueueCapacity(logThreadConfigProperties.getQueueCapacity());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("日志处理异步线程池-");
        executor.initialize();
        return executor;
    }
}
