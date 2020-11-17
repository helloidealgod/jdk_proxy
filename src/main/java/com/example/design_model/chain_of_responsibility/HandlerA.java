package com.example.design_model.chain_of_responsibility;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 14:02
 */
public class HandlerA extends Handler {
    @Override
    public void handleRequest(double scores) {
        if (60 > scores) {
            System.out.println("分数：" + scores + " 不及格");
        } else {
            if (null != nextHandler) {
                nextHandler.handleRequest(scores);
            } else {
                System.out.println("无人处理");
            }
        }
    }
}
