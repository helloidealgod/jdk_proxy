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
//        final Class declaringInterface = findDeclaringInterface(proxy, method);
        System.out.println("类名：" + method.getName());
        System.out.println("参数长度：" + args.length);
        method.getParameterTypes();
        method.getReturnType();
        for (int i = 0; i < args.length; i++) {
            if(args[i] instanceof Integer){

            }
            System.out.println("args[" + i + "]=" + args[i]);
            System.out.println("args[" + i + "]=" + args[i].getClass().getName());
        }
        boolean annotationPresent = method.isAnnotationPresent(Select.class);
        if (annotationPresent) {
            Select select = method.getAnnotation(Select.class);
            System.out.println("执行SQL查询语句 " + select.value());
            JdbcConfig jdbcConfig = new JdbcConfig();
            return jdbcConfig.excuteQuery(select.value(),new ObjectHandler(),method.getReturnType().getName(),null);
        }
        return null;
    }
//    private Class findDeclaringInterface(Object proxy, Method method) {
//        Class declaringInterface = null;
//        for (Class iface : proxy.getClass().getInterfaces()) {
//            try {
//                Method m = iface.getMethod(method.getName(), method.getParameterTypes());
//                if (declaringInterface != null) {
//                    throw new Exception("Ambiguous method mapping.  Two mapper interfaces contain the identical method signature for " + method);
//                } else if (m != null) {
//                    declaringInterface = iface;
//                }
//            } catch (Exception e) {
//                // Intentionally ignore.
//                // This is using exceptions for flow control,
//                // but it's definitely faster.
//            }
//        }
//        if (declaringInterface == null) {
////            throw new BindingException("Could not find interface with the given method " + method);
//            System.out.println("declaringInterface == null");
//            return null;
//        }
//        return declaringInterface;
//    }
}
