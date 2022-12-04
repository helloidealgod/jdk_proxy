package com.example.design_model.status;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/18 09:27
 */
public class EndStatus extends Status {
    @Override
    public void handler() {
        System.out.println("结束阶段");
    }
}
