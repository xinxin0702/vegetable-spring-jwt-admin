package com.github.liuzhuoming23.svea.common.annotation;

import com.github.liuzhuoming23.svea.common.cons.LogLevel;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 *
 * @author liuzhuoming
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 日志描述
     */
    String description();

    /**
     * 自定义日志等级
     */
    LogLevel level() default LogLevel.DEFAULT;
}
