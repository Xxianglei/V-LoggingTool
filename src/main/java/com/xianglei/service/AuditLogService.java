package com.xianglei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xianglei.entity.AuditLog;

/**
 * @Auther: Xianglei
 * @Company:
 * @Date: 2020/6/15 15:15
 * com.sys.honeypot.service
 * @Description：日志业务层
 */
public interface AuditLogService extends IService<AuditLog> {

    /**
     * 更具配置将日志存到文件  mysql  mq等
     * @param auditLog
     */
    void saveLocalOrMysqlOrMQ(AuditLog auditLog);

}
