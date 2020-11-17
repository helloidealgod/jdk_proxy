package com.example.design_model.observe;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 14:31
 */
public class ConcreteObserver1 implements Observer {
    @Override
    public void response() {
        System.out.println("具体观察者1作出反应！");
    }
}
