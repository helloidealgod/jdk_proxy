package com.example.proxy.service;

public class UserServiceImpl implements UserService{

    public String execute()  throws Throwable{
        System.out.println( "===============================执行事务====================================");
        return "Hello world";
    }
}
