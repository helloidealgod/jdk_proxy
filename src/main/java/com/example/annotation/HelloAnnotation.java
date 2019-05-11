package com.example.annotation;

@ClassAnnotation("HelloAnnotation")
public class HelloAnnotation {
    @FieldAnnotation("className")
    public String className;
    @MethodAnnotation("getClassName()")
    public String getClassName() {
        return className;
    }
}
