package com.example.compile.table3.tree.action.impl;

import com.example.compile.table3.tree.Syntax;
import com.example.compile.table3.tree.action.Action;

public class ActionEnd extends Action {
    private String expr;

    public ActionEnd(Syntax syntax, String expr) {
        super(syntax);
        this.expr = expr;
    }

    @Override
    public int explain(String symbol, String token) {
        if (this.expr.equals(token)) {
            return 0;
        }
        return -1;
    }
}
