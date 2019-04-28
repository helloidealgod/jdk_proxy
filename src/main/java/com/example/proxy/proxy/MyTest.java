package com.example.proxy.proxy;

import com.example.proxy.service.UserService;
import com.example.proxy.service.UserServiceImpl;
import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

public class MyTest {
    public static void main(String[] args)  throws Throwable{
        System.out.println( "===============================JDK动态代理开始====================================");
        UserService userService = (UserService) Proxy.newProxyInstance(MyTest.class.getClassLoader(), new Class<?>[]{UserService.class}, new MyHandler(new UserServiceImpl()));
        createProxyClassFile();
        userService.execute();
        System.out.println( "===============================JDK动态代理结束====================================");
        System.out.println( "===============================手写JDK代理开始 ====================================");
        UserService userService1 = (UserService)MyProxy.newProxyInstance(MyTest.class.getClassLoader(), UserService.class, new MyInvocationHandler(new UserServiceImpl()));
        userService1.execute();
        System.out.println( "===============================手写JDK代理结束====================================");
    }

    /**
     * @description: 获取内存中的$Proxy0实列并输出到class文件中
     * @param: void
     * @return: void
     * @auther: xiankun.jiang
     * @date: 2019/4/28 9:17
     */
    public static void createProxyClassFile(){
        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class<?>[]{UserService.class});
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("$Proxy0.class");
            fileOutputStream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
