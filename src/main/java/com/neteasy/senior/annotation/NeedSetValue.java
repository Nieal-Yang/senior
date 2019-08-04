package com.neteasy.senior.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedSetValue {

    //定义注解的属性
    Class<?> beanClass();

    String param();

    String method();

    String targetField();

}
