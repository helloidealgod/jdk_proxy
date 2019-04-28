package com.example.proxy.service;
/**
 * @description: 关于jdk代理，需手写jdk代理来实现如日志、数据库事务等功能的需要写一个interface，并至少有一个类
 * 实现了该interface才能生效（即代码中直接使用了Proxy类或其子类），但是像springboot等的@Transactional事务注解就不
 * 用写interface
 * @param: void
 * @return: String
 * @auther: xiankun.jiang
 * @date: 2019/4/28 9:04
 */
public interface UserService {

    public String execute() throws Throwable;
}
