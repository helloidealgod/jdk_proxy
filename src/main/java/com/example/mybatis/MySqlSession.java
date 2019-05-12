package com.example.mybatis;

import java.lang.reflect.Proxy;

public class MySqlSession {
    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> clas) {
        return (T) Proxy.newProxyInstance(MySqlSession.class.getClassLoader(), new Class[]{clas}, new MapperInvocationHandler());
    }
}
