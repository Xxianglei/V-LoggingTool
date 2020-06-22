package com.xianglei.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @auther: Xianglei
 * @Company:
 * @Date: 2020/6/22 10:28
 * com.xianglei.config
 * @Description: 线程池配置信息类
 */
@Data
@Configuration
@ConfigurationProperties("v-log.thread")
public class LogThreadConfigProperties {
    /**
     * 核心线程数
     */
    private int corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
    /**
     * 最大线程数
     */
    private int maxPoolSize = corePoolSize;
    /**
     * 队列大小
     */
    private int queueCapacity = Runtime.getRuntime().availableProcessors() * 20;

}
