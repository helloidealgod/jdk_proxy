package com.example.design_model.chain_of_responsibility;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 14:05
 */
public class Main {
    public static void main(String[] args) {
        Handler handlerA = new HandlerA();
        Handler handlerB = new HandlerB();
        Handler handlerC = new HandlerC();

        handlerA.setNextHandler(handlerB);
        handlerB.setNextHandler(handlerC);

        handlerA.handleRequest(32);
        handlerA.handleRequest(60);
        handlerA.handleRequest(80);
        handlerA.handleRequest(90);
        handlerA.handleRequest(100);
        handlerA.handleRequest(150);
        handlerA.handleRequest(151);
    }
}
