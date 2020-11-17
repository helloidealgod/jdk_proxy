package com.example.design_model.cmd_model;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 13:49
 */
public class Invoker {
    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void call() {
        System.out.println("调用者执行命令command...");
        command.execute();
    }
}
