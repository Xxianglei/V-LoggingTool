package com.xianglei.service.impl;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xianglei.config.LogCommonConfigProperties;
import com.xianglei.entity.AuditLog;
import com.xianglei.mapper.AuditLogMapper;
import com.xianglei.service.AuditLogService;
import com.xianglei.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: Xianglei
 * @Company:
 * @Date: 2020/6/15 15:16
 * com.sys.honeypot.service.impl
 * @Description:审计日志业务实现层面
 */
@Slf4j
@Service
public class AuditLogServiceImpl extends ServiceImpl<AuditLogMapper, AuditLog> implements AuditLogService {
    @Autowired
    LogCommonConfigProperties logCommonConfigProperties;
    @Autowired
    AuditLogMapper auditLogMapper;

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

    //TODO 文件拓展名 以及 文件内容格式化
    public void transToLocalFileSystem(AuditLog auditLog) {
        String fileNameFormat = logCommonConfigProperties.getNameFormat();
        String storeFilePath = logCommonConfigProperties.getStoreFilePath();
        String extName = logCommonConfigProperties.getExtName();
        storeFilePath = storeFilePath + DateUtil.format(new DateTime(), fileNameFormat) + extName;
        FileUtils.writeContentToFile(auditLog.toString(), storeFilePath, true);
    }

    public void transToMQ(AuditLog auditLog) {

    }
}
