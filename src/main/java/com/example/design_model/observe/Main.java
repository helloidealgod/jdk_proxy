package com.example.design_model.observe;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 14:29
 */
public class Main {
    public static void main(String[] args) {
        Observer observer1 = new ConcreteObserver1();
        Observer observer2 = new ConcreteObserver2();

        Subject subject = new ConcreteSubject();
        subject.add(observer1);
        subject.add(observer2);
        subject.notifyObserver();
    }
}
