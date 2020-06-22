package com.xianglei.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: Xianglei
 * @Company:
 * @Date: 2020/6/21 22:29
 * @Version 1.0
 * 配置项目中文件存储
 * 开启或关闭
 * 文件存储路径
 * 命名格式
 * 配置项目中消息队列
 * 消息中心
 * ...
 */
@Data
@Configuration
@ConfigurationProperties("v-log")
public class LogCommonConfigProperties {
    /**
     * 文件存储默认关闭
     */
    @Value("${v-log.file.open:false}")
    private boolean fileOpen;
    /**
     * 文件存储路径  默认跟目录的logs文件夹
     */
    @Value("${v-log.file.storeFilePath:C:\\logs}")
    private String storeFilePath;
    /**
     * 文件命名格式 默认年月日时分秒
     */
    @Value("${v-log.file.nameFormat:yyyyMMddHHmmss}")
    private String nameFormat;
    /**
     * 文件格式  默认.txt
     */
    @Value("${v-log.file.nameFormat:.txt}")
    private String extName;
    /**
     * 消息队列是否开启  默认关闭
     */
    @Value("${v-log.rocketmq.open:false}")
    private boolean mqOpen = false;


}
