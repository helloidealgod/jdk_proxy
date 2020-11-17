package com.example.design_model.memento_pattern;

/**
 * @Description:  发起人
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 14:56
 */
public class Originator {
    private String state;

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public Memento createMemento() {
        return new Memento(state);
    }

    public void restoreMemento(Memento m) {
        this.setState(m.getState());
    }
}
