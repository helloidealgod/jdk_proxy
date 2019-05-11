package com.example.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MyTest {
    public static void main(String[] argv) {
        HelloAnnotation annotation = new HelloAnnotation();
        Class clazz = annotation.getClass();
        Method[] methods = clazz.getMethods();
        Field[] fields = clazz.getFields();
        boolean annotationPresent = clazz.isAnnotationPresent(ClassAnnotation.class);
        boolean annotationPresent1 = methods[0].isAnnotationPresent(MethodAnnotation.class);
        boolean annotationPresent2 = fields[0].isAnnotationPresent(FieldAnnotation.class);
        if (annotationPresent) {
            ClassAnnotation classAnnotation = (ClassAnnotation) clazz.getAnnotation(ClassAnnotation.class);
            System.out.println(clazz.getName() + " has annotation, value = " + classAnnotation.value());
        }
        if (annotationPresent1) {
            MethodAnnotation annotation1 = methods[0].getAnnotation(MethodAnnotation.class);
            System.out.println(methods[0].getName() + " has annotation, value = " + annotation1.value());
        }
        if (annotationPresent2) {
            FieldAnnotation annotation2 = fields[0].getAnnotation(FieldAnnotation.class);
            System.out.println(fields[0].getName() + " has annotation, value = " + annotation2.value());
        }
    }
}
