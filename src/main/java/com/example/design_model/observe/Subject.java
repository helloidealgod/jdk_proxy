package com.example.design_model.observe;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 14:31
 */
public abstract class Subject {
    protected List<Observer> observers = new ArrayList<Observer>();

    public void add(Observer observer) {
        observers.add(observer);
    }

    public void remove(Observer observer) {
        observers.remove(observer);
    }
    public abstract void notifyObserver();
}
