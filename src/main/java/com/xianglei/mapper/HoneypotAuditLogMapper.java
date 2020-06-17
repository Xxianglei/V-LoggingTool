package com.xianglei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xianglei.entity.HoneypotAuditLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Auther: Xianglei
 * @Company:
 * @Date: 2020/6/15 15:18
 * com.sys.honeypot.mapper
 * @Description:审计日志持久层
 */
@Mapper
public interface HoneypotAuditLogMapper extends BaseMapper<HoneypotAuditLog> {
}
