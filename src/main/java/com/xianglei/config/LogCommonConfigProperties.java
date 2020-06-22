package com.xianglei.config;

import lombok.Data;
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
@ConfigurationProperties("v-log.file")
public class LogCommonConfigProperties {
    /**
     * 文件存储默认关闭
     */
    private boolean fileOpen=false;
    /**
     * 文件存储路径  默认跟目录的logs文件夹
     */
    private String storeFilePath="C:\\logs";
    /**
     * 文件命名格式 默认年月日时分秒
     */
    private String nameFormat="yyyyMMddHHmmss";
    /**
     * 文件格式  默认.txt
     */
    private String extName=".txt";
    /**
     * 消息队列是否开启  默认关闭
     */
    private boolean mqOpen = false;


}
