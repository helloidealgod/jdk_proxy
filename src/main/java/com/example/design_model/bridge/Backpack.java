package com.example.design_model.bridge;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/18 09:40
 */
public class Backpack extends Bag {
    @Override
    public String getName() {
        System.out.println(color.getColor() + "双肩背包");
        return color.getColor() + "双肩背包";
    }
}
