package com.example.design_model.proxy;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 15:03
 */
public class RealSubject implements Subject {
    @Override
    public void request() {
        System.out.println("实际对象处理...");
    }
}
