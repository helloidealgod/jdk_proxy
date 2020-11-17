package com.example.design_model.template_pattern;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 15:21
 */
public abstract class Template {
    public void templateMethod() {
        specificMethod();
        abstractMethod1();
        abstractMethod2();
    }

    public void specificMethod() {
        System.out.println("抽象类中的具体方法被调用...");
    }

    public abstract void abstractMethod1();

    public abstract void abstractMethod2();
}
