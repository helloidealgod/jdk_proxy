package com.example.design_model.visitor;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/18 09:04
 */
public interface Visitor {
    public void visit(ConcreteElementA concreteElementA);

    public void visit(ConcreteElementB concreteElementB);
}
