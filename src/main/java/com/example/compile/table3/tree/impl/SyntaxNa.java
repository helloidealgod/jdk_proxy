package com.example.compile.table3.tree.impl;

import com.example.compile.table3.tree.Syntax;
import com.example.compile.table3.tree.action.impl.ActionE;
import com.example.compile.table3.tree.action.impl.ActionEList;
import com.example.compile.table3.tree.action.impl.ActionEnd;
import com.example.compile.table3.tree.action.impl.ActionNa;

public class SyntaxNa extends Syntax {
    private String[][] segment = {
            {"Na", "=", "E", ";"},
            {"Na", "(", "EList", ")", ";"},
    };

    public SyntaxNa() {
        super();
        actionStack.push(new ActionNa(this));
        actionStack.push(new ActionNa(this));
    }

    @Override
    public int execute(String symbol) {
        return 0;
    }

    @Override
    public int execute(String symbol, String token) {
        if (actionStack.isEmpty()) {
            if ("=".equals(token)) {
                actionStack.push(new ActionEnd(this, ";"));
                actionStack.push(new ActionE(this));
            } else if ("(".equals(token)) {
                actionStack.push(new ActionEnd(this, ";"));
                actionStack.push(new ActionEnd(this, ")"));
                actionStack.push(new ActionEList(this));
            }
        } else {
            int ret = actionStack.getTop().explain(symbol, token);
            if (ret == 0) {
                actionStack.pop();
            }
        }
        return 0;
    }
}
