package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassAnnotation {
    //设置默认值的时候，在使用注解的时候不用@MethodAnnotation("") 或 @MethodAnnotation(value="")
    String value() default "";
}
