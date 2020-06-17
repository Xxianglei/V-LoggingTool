package com.xianglei.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xianglei.entity.HoneypotAuditLog;
import com.xianglei.mapper.HoneypotAuditLogMapper;
import com.xianglei.service.HoneypotAuditLogService;
import lombok.extern.slf4j.Slf4j;
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
public class HoneypotAuditLogServiceImpl extends ServiceImpl<HoneypotAuditLogMapper, HoneypotAuditLog> implements HoneypotAuditLogService {
}
