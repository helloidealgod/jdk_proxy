package com.example.design_model.visitor;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/18 09:06
 */
public class ConcreteElementB implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String operationB() {
        return "具体元素B的操作。";
    }
}
