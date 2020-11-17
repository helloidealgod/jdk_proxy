package com.example.design_model.template_pattern;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 15:24
 */
public class RealTemplateOne extends Template {
    @Override
    public void abstractMethod1() {
        System.out.println("实例1：算式1");
    }

    @Override
    public void abstractMethod2() {
        System.out.println("实例1：算式2");
    }
}
