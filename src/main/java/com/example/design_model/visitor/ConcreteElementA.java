package com.example.design_model.visitor;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/18 09:05
 */
public class ConcreteElementA implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String operationA() {
        return "具体元素A的操作。";
    }
}
