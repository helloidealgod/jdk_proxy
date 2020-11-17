package com.example.design_model.chain_of_responsibility;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 14:02
 */
public abstract class Handler {
    protected Handler nextHandler;

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public Handler getNextHandler() {
        return this.nextHandler;
    }

    public abstract void handleRequest(double scores);
}
