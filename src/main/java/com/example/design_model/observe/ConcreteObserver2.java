package com.example.design_model.observe;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 14:31
 */
public class ConcreteObserver2 implements Observer {
    @Override
    public void response() {
        System.out.println("具体观察者2作出反应！");
    }
}
