package com.example.mybatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperInvocationHandler implements InvocationHandler {
    private Object object;

    public MapperInvocationHandler() {
    }

    public MapperInvocationHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("类名：" + method.getName());
        System.out.println("参数长度：" + args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.println("args[" + i + "]=" + args[i]);
            System.out.println("args[" + i + "]=" + args[i].getClass().getName());
        }
        boolean annotationPresent = method.isAnnotationPresent(Select.class);
        if (annotationPresent) {
            Select select = method.getAnnotation(Select.class);
            System.out.println("执行SQL查询语句 " + select.value());
            JdbcConfig jdbcConfig = new JdbcConfig();
            return jdbcConfig.excuteQuery(select.value(),new Handler(),null);
        }
        return null;
    }
}
