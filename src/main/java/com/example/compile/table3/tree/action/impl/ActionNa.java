package com.example.compile.table3.tree.action.impl;

import com.example.compile.table3.tree.Syntax;
import com.example.compile.table3.tree.action.Action;
import com.example.compile.table3.tree.impl.SyntaxTyp;

public class ActionNa extends Action {

    public ActionNa(Syntax syntax) {
        super(syntax);
    }

    @Override
    public int explain(String symbol, String token) {
        ((SyntaxTyp)syntax).name = token;
        return 0;
    }
}
