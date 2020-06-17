package com.xianglei.constant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @description MqResponse 请求 响应 状态码
 * @author Xianglei
 * @date 2020-06-03
 */
@Data
public class CommonResponse<T> {

    /**
     *
     * common response
     * 请求状态码(成功/错误码)
     */
    @JsonProperty("code")
    private int code;
    /**
     * 请求状态或失败原因描述
     */
    @JsonProperty("msg")
    private String msg;
    /**
     * 返回与请求的对应的信息
     */
    @JsonProperty("data")
    private Object data;

    public CommonResponse() {
        this.code = ErrCodeEnum.SUCCESS.getCode();
        this.msg = ErrCodeEnum.SUCCESS.getMsg();
    }

    public CommonResponse(ErrCodeEnum error) {
        this.code = error.getCode();
        this.msg = error.getMsg();
    }

    public CommonResponse(ErrCodeEnum error, T data) {
        this.code = error.getCode();
        this.msg = error.getMsg();
        this.data = data;
    }

    public CommonResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CommonResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public void setValue(int code, String msg) {

        this.code = code;
        this.msg = msg;
    }

    public void setValue(int code, String msg, T data) {

        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public void setValue(ErrCodeEnum CodeEnum) {

        this.code = CodeEnum.getCode();
        this.msg = CodeEnum.getMsg();
    }

    public void setValue(ErrCodeEnum codeEnum, T data) {

        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
        this.data = data;
    }
}
