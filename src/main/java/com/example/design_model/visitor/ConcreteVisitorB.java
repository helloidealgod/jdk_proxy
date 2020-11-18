package com.example.design_model.visitor;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/18 09:11
 */
public class ConcreteVisitorB implements Visitor {
    @Override
    public void visit(ConcreteElementA element) {
        System.out.println("具体访问者B访问-->" + element.operationA());
    }

    @Override
    public void visit(ConcreteElementB element) {
        System.out.println("具体访问者B访问-->" + element.operationB());
    }
}
