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
    protected int code;
    /**
     * 请求状态或失败原因描述
     */
    @JsonProperty("message")
    protected String message;
    /**
     * 返回与请求的对应的信息
     */
    @JsonProperty("data")
    protected T data;

    public CommonResponse() {
        this.code = ErrCodeEnum.SUCCESS.getCode();
        this.message = ErrCodeEnum.SUCCESS.getMsg();
    }

    public CommonResponse(ErrCodeEnum error) {
        this.code = error.getCode();
        this.message = error.getMsg();
    }

    public CommonResponse(ErrCodeEnum error, T data) {
        this.code = error.getCode();
        this.message = error.getMsg();
        this.data = data;
    }

    public CommonResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public void setValue(int code, String msg) {

        this.code = code;
        this.message = msg;
    }

    public void setValue(int code, String msg, T data) {

        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public void setValue(ErrCodeEnum CodeEnum) {

        this.code = CodeEnum.getCode();
        this.message = CodeEnum.getMsg();
    }

    public void setValue(ErrCodeEnum codeEnum, T data) {

        this.code = codeEnum.getCode();
        this.message = codeEnum.getMsg();
        this.data = data;
    }
}
