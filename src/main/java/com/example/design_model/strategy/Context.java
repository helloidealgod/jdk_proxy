package com.example.design_model.strategy;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 15:19
 */
public class Context {
    public void strategyMethod(int method) {
        if (1 == method) {
            new StrategyOne().strategyMethod();
        } else if (2 == method) {
            new StrategyTwo().strategyMethod();
        } else {
            System.out.println("无此策略");
        }
    }
}
