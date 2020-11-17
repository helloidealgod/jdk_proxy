package com.example.design_model.chain_of_responsibility;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 14:02
 */
public class HandlerC extends Handler {

    @Override
    public void handleRequest(double scores) {
        if (90 <= scores && 150 >= scores) {
            System.out.println("分数：" + scores + " 优秀");
        } else {
            if (null != nextHandler) {
                nextHandler.handleRequest(scores);
            } else {
                System.out.println("无人处理");
            }
        }
    }
}
