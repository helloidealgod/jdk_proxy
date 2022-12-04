package com.example.design_model.status;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/18 09:27
 */
public class RunningStatus extends Status {
    @Override
    public void handler() {
        System.out.println("运行阶段");
        new EndStatus().handler();
    }
}
