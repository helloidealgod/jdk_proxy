package com.example.design_model.visitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/18 09:08
 */
public class ObjectStructure {
    private List<Element> elementList = new ArrayList<>();

    public void add(Element element) {
        elementList.add(element);
    }

    public void remove(Element element) {
        elementList.remove(element);
    }

    public void accept(Visitor visitor) {
        Iterator<Element> i = elementList.iterator();
        while (i.hasNext()) {
            ((Element) i.next()).accept(visitor);
        }
    }
}
