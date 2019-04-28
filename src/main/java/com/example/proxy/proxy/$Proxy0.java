package com.example.proxy.proxy;

import java.lang.reflect.Method;

public class $Proxy0 implements com.example.proxy.service.UserService {
    MyInvocationHandler h;

    public $Proxy0(MyInvocationHandler h) {
        this.h = h;
    }

    public String execute() throws Throwable {
        Method md = com.example.proxy.service.UserService.class.getMethod("execute", new Class[]{});
        return (String) this.h.invoke(this, md, null);
    }

}