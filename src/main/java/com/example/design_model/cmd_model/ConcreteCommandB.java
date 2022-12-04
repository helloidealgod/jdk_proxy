package com.example.design_model.cmd_model;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 13:50
 */
public class ConcreteCommandB implements Command {
    private ReceiverB receiverB;

    ConcreteCommandB() {
        receiverB = new ReceiverB();
    }

    @Override
    public void execute() {
        receiverB.action();
    }
}
