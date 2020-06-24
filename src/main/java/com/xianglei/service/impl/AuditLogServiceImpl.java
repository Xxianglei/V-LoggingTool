package com.xianglei.service.impl;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xianglei.config.LogCommonConfigProperties;
import com.xianglei.entity.AuditLog;
import com.xianglei.mapper.AuditLogMapper;
import com.xianglei.service.AuditLogService;
import com.xianglei.util.FileUtils;
import com.xianglei.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @Auther: Xianglei
 * @Company:
 * @Date: 2020/6/15 15:16
 * com.sys.honeypot.service.impl
 * @Description:日志业务实现层面
 */
@Slf4j
@Service
public class AuditLogServiceImpl extends ServiceImpl<AuditLogMapper, AuditLog> implements AuditLogService {

    @Value("${rocketmq.producer.topic:v-log-topic}")
    private String topic;
    @Autowired
    LogCommonConfigProperties logCommonConfigProperties;
    @Autowired
    AuditLogMapper auditLogMapper;
    @Autowired
    RocketMQTemplate rocketMQTemplate;

    // 异步调用
    @Async("VLogThreadPool")
    @Override
    public void saveLocalOrMysqlOrMQ(AuditLog auditLog) {
        boolean fileOpen = logCommonConfigProperties.isFileOpen();
        boolean mqOpen = logCommonConfigProperties.isMqOpen();
        // 存到数据库
        auditLogMapper.insert(auditLog);
        if (fileOpen) {
            transToLocalFileSystem(auditLog);
        }
        if (mqOpen) {
            transToMQ(auditLog);
        }

    }

    // TODO 多文件拓展名支持
    public void transToLocalFileSystem(AuditLog auditLog) {
        String fileNameFormat = logCommonConfigProperties.getNameFormat();
        String storeFilePath = logCommonConfigProperties.getStoreFilePath();
        try {
            storeFilePath = FileUtils.getOsInfoAndMkdir(storeFilePath);
            // 选择文件扩展名
            String extName = logCommonConfigProperties.getExtName();
            storeFilePath = storeFilePath + File.separator + DateUtil.format(new DateTime(), fileNameFormat) + extName;
            StringBuilder content = new StringBuilder();
            content.append("ID:").append(auditLog.getFlowId()).append("  ")
                    .append("访问者:").append(auditLog.getUserName()).append("  ")
                    .append("访问IP:").append(auditLog.getLoginIp()).append("  ")
                    .append("日志类型:").append(auditLog.getLogType()).append("  ")
                    .append("日志详情:").append(auditLog.getDetails()).append("  ")
                    .append("操作时间:").append(auditLog.getOperationTime()).append("\n");
            FileUtils.writeContentToFile(content.toString(), storeFilePath, true);
        } catch (RuntimeException e) {
            log.error("Error in local file store {}", e);
        }

    }

    public void transToMQ(AuditLog auditLog) {
        String json = JsonUtils.toJson(auditLog);
        rocketMQTemplate.sendOneWay(topic, json);
    }
}
