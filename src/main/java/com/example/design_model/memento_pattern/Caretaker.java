package com.example.design_model.memento_pattern;

/**
 * @Description: 管理者
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 14:57
 */
public class Caretaker {
    private Memento memento;

    public void setMemento(Memento m) {
        memento = m;
    }

    public Memento getMemento() {
        return memento;
    }
}
