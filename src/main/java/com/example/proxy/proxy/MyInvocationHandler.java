package com.example.proxy.proxy;

import com.example.proxy.service.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler{
    private UserService userService;

    public MyInvocationHandler(UserService userService) {
        this.userService = userService;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //开启事务
        before();
        //调用目标代理的业务方法
        method.invoke(userService,args);
        //关闭事务
        after();
        return null;
    }
    public static void before(){
        System.out.println("===============================开启事务====================================");
    }
    public static void after(){
        System.out.println("===============================关闭事务====================================");
    }
}
