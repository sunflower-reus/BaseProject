package com.views.network.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 序列化时需要加密该字段
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SerializedEncryption {
    /**
     * 加密类型：
     * RSA-Base64
     * RSA-BCD
     * 默认是 Base64
     */
    String type() default "Base64";
}