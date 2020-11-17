package com.example.design_model.mediator_pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: xiankun.jiang
 * @Date: 2020/11/17 14:37
 */
public class ConcreteMediator extends Mediator {
    private List<Colleague> colleagues = new ArrayList<Colleague>();

    @Override
    public void register(Colleague colleague) {
        if (!colleagues.contains(colleague)) {
            colleagues.add(colleague);
            colleague.setMedium(this);
        }
    }

    @Override
    public void relay(Colleague cl) {
        for (Colleague ob : colleagues) {
            if (!ob.equals(cl)) {
                ((Colleague) ob).receive();
            }
        }
    }
}