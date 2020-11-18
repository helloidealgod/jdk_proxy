package com.example.design_model.bridge;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/18 09:38
 */
public class Main {
    public static void main(String[] args) {
        Color red = new RedColor();
        Color black = new BlackColor();
        Bag bag = new Backpack();
        Bag bag1 = new BodyBag();

        bag.setColor(red);
        bag.getName();
        bag.setColor(black);
        bag.getName();

        bag1.setColor(red);
        bag1.getName();
        bag1.setColor(black);
        bag1.getName();
    }
}
