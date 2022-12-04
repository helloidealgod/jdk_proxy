package com.example.design_model.visitor;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/18 09:07
 */
public interface Element {
    public void accept(Visitor visitor);
}
