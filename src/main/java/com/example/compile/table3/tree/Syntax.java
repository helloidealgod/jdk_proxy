package com.example.compile.table3.tree;

import com.example.compile.table3.stack.ActionStack;

public abstract class Syntax {
    public int index = 0;
    public int syntaxType = 0;

    public ActionStack actionStack = new ActionStack();

    public abstract int execute(String symbol, String token);

    public abstract int execute(String symbol);
}
