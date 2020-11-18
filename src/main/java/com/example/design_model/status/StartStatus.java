package com.example.design_model.status;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/18 09:26
 */
public class StartStatus extends Status {
    @Override
    public void handler() {
        System.out.println("起始阶段");
        new RunningStatus().handler();
    }
}
