package com.example.design_model.memento_pattern;

/**
 * @Description: 备忘录
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 14:56
 */
public class Memento {
    private String state;

    public Memento(String state) {
        this.state = state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
