package com.xianglei.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther: Xianglei
 * @Company:
 * @Date: 2020/6/17 15:07
 * com.xianglei.config
 * @Description:mq
 */
@Slf4j
@Configuration
public class RocketMqConfiguration {

    @Bean("myRocketMQ")
    @ConditionalOnMissingBean
    public RocketMQTemplate dataSource() {
        log.info("Start initializing RocketMQ ......");
        RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
        log.info("Initialization of RocketMQ completed");
        return rocketMQTemplate;
    }
}
