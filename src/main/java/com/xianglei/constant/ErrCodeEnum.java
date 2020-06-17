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

    NOT_FOUND(404, "Not found"),

    /**
     * 用户管理
     */
    PEOPLE_NUM_REACH_MAX(10001,"当前角色的用户数超出上限"),
    DEL_USER_FAIL(10002,"删除用户失败"),

    /**
     * 登录认证 登出
     */
    LOGIN_PWD_OR_USERAME_ERR(20001,"用户名或密码错误"),
    LOGOUT_FAIL(20002,"登录失效"),
    NO_AUTH_TO_OP(20003,"当前登录已失效，请重新登录"),
    CODE_NOT_RIGHT(20004,"验证码错误"),
    CODE_FAILURE(20005,"验证码已失效");


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
