package com.xianglei.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
/**
 * @Auther: Xianglei
 * @Company: venusgroup
 * @Date: 2020/6/2 14:04
 * @Version 1.0
 */

/**
 * @Target：设定注解的作用域： TYPE声明在类上或枚举上或者是注解上
 * FIELD声明在字段上
 * METHOD声明在方法上
 * PARAMETER声明在形参列表中
 * CONSTRUCTOR声明在构造方法上
 * LOCAL_VARIABLE声明在局部变量上
 */
@Target({ElementType.METHOD})
/**
 * @Retention：注解保留时期
 * SOURCE：编译时注解，只在源码时期存在，编译成.class文件就不存在
 * CLASS：编译时注解，注解在源码时期和.class文件中都存在，运行时没起作用。例如：@Override、@Suppvisewarning、@Deprecated
 * RUNTIME：运行时注解,在代码运行时期还起作用，也就是一直保留, 通常也都用这个，例如：@Autowired
 */
@Retention(RetentionPolicy.RUNTIME)
/**
 * @Inherited：声明允许被标注的类可以在子类继承该注解
 */
@Inherited
/**
 * @Documented:声明在生成javadoc文档时会生成该注解的文档内容
 */
@Documented
/**
 * @interface : 使用该类型声明一个注解
 */
public @interface VLogHunter {

    enum MethodType {
        GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE
    }

    @AliasFor("name")
    String[] value() default {};

    @AliasFor("value")
    String[] name() default {};

    boolean isLog() default true;

    MethodType method() default MethodType.GET;

    String description() default "";

}
