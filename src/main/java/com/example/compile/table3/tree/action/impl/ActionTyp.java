package com.example.compile.table3.tree.action.impl;

import com.example.compile.table3.tree.Syntax;
import com.example.compile.table3.tree.action.Action;
import com.example.compile.table3.tree.impl.SyntaxTyp;

public class ActionTyp extends Action {

    public ActionTyp(Syntax syntax) {
        super(syntax);
    }

    @Override
    public int explain(String symbol, String token) {
        ((SyntaxTyp)syntax).type = token;
        return 0;
    }
}
