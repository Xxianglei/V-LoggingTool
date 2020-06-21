package com.xianglei.aspect;

import cn.hutool.core.date.DateTime;
import com.xianglei.constant.AuditLogTypeEnum;
import com.xianglei.constant.CommonResponse;
import com.xianglei.constant.ErrCodeEnum;
import com.xianglei.constant.VLogHunter;
import com.xianglei.entity.AuditLog;
import com.xianglei.service.AuditLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * @Auther: Xianglei
 * @Company:
 * @Date: 2020/6/15 20:34
 * @Version 1.0
 */
@Slf4j
@Aspect
@Component
public class AuditLogAspect {
    @Autowired
    AuditLogService logService;
    /**
     * 线程内部变量隔离
     */
    private static final ThreadLocal<String> USER_NAME_LOCAL = new ThreadLocal();
    private static final ThreadLocal<String> USER_IP_LOCAL = new ThreadLocal();
    /**
     * 操作结果常量
     */
    public static final String SUCCESS = "成功";
    public static final String FAILS = "失败";


    // 注解切点
    @Pointcut("@annotation(com.xianglei.constant.VLogHunter)")
    public void annotation() {
    }

    @Before("annotation()")
    public void before() {
        log.info("audit log before advice start");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获取用户IP
        String ip = getIpAddr(request);
        USER_IP_LOCAL.set(ip);
        // 获取用户名称
        String userName = "";
        HttpSession session = request.getSession();
        if (session != null) {
            userName = (String) session.getAttribute("userName");
            USER_NAME_LOCAL.set(userName);
        }
        log.info("audit log before advice end");
    }

    // 对注解进行后置返回增强
    @AfterReturning(pointcut = "annotation()", returning = "rc")
    public void doAfterReturning(JoinPoint pjp, CommonResponse rc) throws Throwable {
        try {
            MethodSignature ms = (MethodSignature) pjp.getSignature();
            Method method = ms.getMethod();
            // 获取注解的参数信息
            VLogHunter vLogHunter = method.getAnnotation(VLogHunter.class);
            // 获取操作时间
            DateTime operateTime = DateTime.now();
            // 日志类型
            AuditLogTypeEnum auditLogTypeEnum = vLogHunter.logType();
            String logType = auditLogTypeEnum.getMsg();
            // 封装日志实体
            AuditLog auditLog = new AuditLog();
            auditLog.setLoginIp(USER_IP_LOCAL.get());
            auditLog.setLogType(logType);
            auditLog.setUserName(USER_NAME_LOCAL.get());
            auditLog.setOperationTime(operateTime);
            if (rc != null) {
                String details = vLogHunter.description();
                int code = rc.getCode();
                String module = vLogHunter.module().toString();
                // 如果是全局异常中注入则直接获取异常信息   XXX操作失败xxx错误
                if (VLogHunter.Module.ControllerAdvice.toString().equals(module)) {
                    auditLog.setDetails(details + FAILS + rc.getMsg());
                } else {
                    // 注入模块的功能描述信息  xxx操作成功/失败
                    if (code == ErrCodeEnum.SUCCESS.getCode()) {
                        auditLog.setDetails(details + SUCCESS);
                    } else {
                        auditLog.setDetails(details + FAILS);
                    }
                }
                logService.saveLocalOrMysqlOrMQ(auditLog);
            }
        } catch (Exception e) {
            log.error("Error occured while auditing, cause by: ", e);
        }
    }

    /**
     * 获取用户真实IP地址
     *
     * @return ip
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
