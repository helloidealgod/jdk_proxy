package com.example.design_model.cmd_model;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 13:52
 */
public class Main {
    public static void main(String[] args) {
        Command command = new ConcreteCommandA();
        Invoker invoker = new Invoker(command);
        invoker.call();
        command = new ConcreteCommandB();
        invoker.setCommand(command);
        invoker.call();
    }
}
