package com.xianglei.constant;

/**
 * @description CodeEnum  返回状态及错误信息
 * @author Xianglei
 * @date 2020-06-03
 */
public enum ErrCodeEnum {

    SUCCESS(200, "Success"),

    ERR_SYSTEM(500, "System error"),

    BAD_REQUEST(400,"Bad request"),

    FORBIDDEN(403, "Forbidden"),

    NOT_FOUND(404, "Not found");

    private int code;
    private String msg;

    private ErrCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {

        return code;
    }

    public String getMsg() {

        return msg;
    }

    @Override
    public String toString() {
        return "[" + this.code + "]" + this.msg;
    }
}
