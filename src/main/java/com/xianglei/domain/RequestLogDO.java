package com.xianglei.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @Auther: Xianglei
 * @Company: venusgroup
 * @Date: 2020/6/2 14:04
 * @Version 1.0
 * 请求日志持久化对象
 */
@TableName("mylog")
public class RequestLogDO {

    @TableId("flowId")
    private String flowId;

    @TableField("createDate")
    private Date createDate;

    @TableField("requestUrl")
    private String requestUrl;

    @TableField("requestType")
    private String requestType;

    @TableField("requestParams")
    private String requestParams;

    @TableField("requestCategory")
    private String requestCategory;

    @TableField("requestDesc")
    private String requestDesc;

    @TableField("session")
    private String session;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getRequestCategory() {
        return requestCategory;
    }

    public void setRequestCategory(String requestCategory) {
        this.requestCategory = requestCategory;
    }

    public String getRequestDesc() {
        return requestDesc;
    }

    public void setRequestDesc(String requestDesc) {
        this.requestDesc = requestDesc;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
