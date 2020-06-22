package com.xianglei.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
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
@ConfigurationProperties("v-log")
public class LogThreadConfigProperties {

    @Value("${v-log.file.core-size:2}")
    private int corePoolSize;
    @Value("${v-log.thread.max-size:4}")
    private int maxPoolSize;
    @Value("${v-log.thread.queue-size:50}")
    private int queueCapacity;

}
