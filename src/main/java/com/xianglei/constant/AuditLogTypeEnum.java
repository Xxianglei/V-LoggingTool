package com.xianglei.constant;

/**
 * @Auther: Xianglei
 * @Company:
 * @Date: 2020/6/15 21:34
 * @Version 1.0
 * 日志类型
 */
public enum AuditLogTypeEnum {

    ACCOUNT_MANAGE("账户管理"),
    WARNING_LOG("告警日志"),
    COMMON_LOG("公共日志");

    private String msg;

    AuditLogTypeEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
