package com.bear.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author panda.
 * @since 2018-11-25
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

    /**
     * 日志模块
     *
     * @return
     */
    String module();

    /**
     * 记录参数<br>
     * 尽量记录普通参数类型的方法，和能序列化的对象
     *
     * @return
     */
    boolean recordParam() default true;
}
