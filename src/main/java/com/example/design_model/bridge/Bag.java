package com.example.design_model.bridge;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/18 09:39
 */
public abstract class Bag {
    Color color;
    String name;

    public void setColor(Color color) {
        this.color = color;
    }

    public abstract String getName();
}
