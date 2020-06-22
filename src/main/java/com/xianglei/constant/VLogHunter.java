package com.xianglei.constant;

import java.lang.annotation.*;

/**
 * @auther: Xianglei
 * @Company:
 * @Date: 2020/6/2 14:04
 * @Version 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface VLogHunter {

    enum MethodType {
        GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE
    }

    enum Module {
        CONTROLLER, SERVICE, ControllerAdvice
    }

    /**
     * 可以默认缺省
     *
     * @return
     */
    String name() default "";

    /**
     * 请求方式
     *
     * @return
     */
    MethodType method() default MethodType.GET;

    /**
     * 日志类型
     *
     * @return
     */
    AuditLogTypeEnum logType() default AuditLogTypeEnum.COMMON_LOG;

    /**
     * 操作描述信息   格式：XXX 操作
     *
     * @return
     */
    String description() default "";

    /**
     * 注解使用模块  默认 CONTROLLER及控制层使用，使用ControllerAdvice/CONTROLLER会影响最终生成的操作详情描述信息
     *
     * @return
     */
    Module module() default Module.CONTROLLER;

}
