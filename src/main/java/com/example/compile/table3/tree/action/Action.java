package com.example.compile.table3.tree.action;

import com.example.compile.table3.tree.Syntax;

public abstract class Action {
    public Syntax syntax;

    public Action(Syntax syntax) {
        this.syntax = syntax;
    }

    public abstract int explain(String symbol, String token);
}
