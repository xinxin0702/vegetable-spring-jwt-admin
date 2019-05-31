package com.github.liuzhuoming23.vegetable.admin.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求次数限制
 *
 * @author liuzhuoming
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLimit {

    /**
     * 限制次数
     */
    int count() default 10;

    /**
     * 间隔时间（ms）
     */
    long interval() default 60 * 1000;
}
