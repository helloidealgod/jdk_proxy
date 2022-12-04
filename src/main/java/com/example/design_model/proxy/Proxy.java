package com.example.design_model.proxy;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 15:04
 */
public class Proxy {
    Subject subject = new RealSubject();

    public void request() {
        preRequest();
        subject.request();
        postRequest();
    }

    public void preRequest() {
        System.out.println("访问真实主题之前的预处理。");
    }

    public void postRequest() {
        System.out.println("访问真实主题之后的后续处理。");
    }
}
