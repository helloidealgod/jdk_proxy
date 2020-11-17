package com.example.design_model.strategy;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 15:15
 */
public class Main {
    public static void main(String[] args) {
        Context context = new Context();
        context.strategyMethod(0);
        context.strategyMethod(1);
        context.strategyMethod(2);
        context.strategyMethod(3);
    }
}
