package com.example.design_model.cmd_model;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 13:50
 */
public class ConcreteCommandA implements Command{
    private ReceiverA receiverA;
    ConcreteCommandA() {
        receiverA = new ReceiverA();
    }

    @Override
    public void execute() {
        receiverA.action();
    }
}
