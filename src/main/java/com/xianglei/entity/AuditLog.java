package com.xianglei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: Xianglei
 * @Company:
 * @Date: 2020/6/15 14:34
 * com.sys.honeypot.entity
 * @Description:日志实体
 */
@Data
@TableName(value = "honeypot_audit_log")
public class AuditLog implements Serializable {
    private static final long serialVersionUID = 6961827819124668744L;
    /**
     * 流水id
     */
    @TableId(value = "flow_id", type = IdType.AUTO)
    private Integer flowId;
    /**
     * 用户名称
     */
    @TableField(value = "user_name")
    private String userName;
    /**
     * 登录IP
     */
    @TableField(value = "login_ip")
    private String loginIp;
    /**
     * 日志类型
     */
    @TableField(value = "log_type")
    private String logType;
    /**
     * 日志详情
     */
    @TableField(value = "details")
    private String details;

    /**
     * 操作时间
     */
    @TableField(value = "operation_time")
    private Date operationTime;

}
