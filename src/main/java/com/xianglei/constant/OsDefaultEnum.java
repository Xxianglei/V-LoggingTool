package com.xianglei.constant;

/**
 * @auther: Xianglei
 * @Date: 2020/6/22 11:27
 * com.xianglei.constant
 * @Description:操作系统枚举
 */
public enum OsDefaultEnum {
    /**
     * windows 默认路径
     */
    WINDOWS("windows", "C:\\logs"),
    /**
     * linux默认路径
     */
    LINUX("linux", "/usr/local");

    private String msg;
    private String path;

    OsDefaultEnum(String msg, String path) {
        this.msg = msg;
        this.path = path;
    }

    public String getInfo() {
        return msg;
    }

    public String getPath() {
        return path;
    }
}