package com.example.design_model.adapter;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 15:09
 */
public class Adapter extends RealAdapter implements Target {
    RealAdapter realAdapter = new RealAdapter();

    @Override
    public void request() {
        realAdapter.specificRequest();
    }
}
