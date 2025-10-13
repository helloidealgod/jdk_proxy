package com.example.compile.table3.tree.impl;

import com.example.compile.table3.tree.Syntax;
import com.example.compile.table3.tree.action.impl.*;

public class SyntaxTyp extends Syntax {
    public String type;
    public String name;
    public ActionE actionE;
    private String[][] segment = {
            {"Typ", "Na", ";"},
            {"Typ", "Na", "=", "E", ";"},
            {"Typ", "Na", "(", "VdList", ")", "Block"},
    };

    public SyntaxTyp() {
        super();
        this.actionStack.push(new ActionNa(this));
        this.actionStack.push(new ActionTyp(this));
    }

    @Override
    public int execute(String symbol) {
        if ("{Es'}".equals(symbol)) {
            actionStack.push(this.actionE);
        } else if ("{Ee'}".equals(symbol)) {
            actionStack.getTop().explain(null, symbol);
            actionStack.pop();
        }
        return 0;
    }

    @Override
    public int execute(String symbol, String token) {
        if (actionStack.isEmpty()) {
            if (";".equals(token)) {

            } else if ("=".equals(token)) {
                this.actionE = new ActionE(this);
                //actionStack.push(new ActionE(this));
            } else if ("(".equals(token)) {
                actionStack.push(new ActionBlock(this));
                actionStack.push(new ActionEnd(this, ")"));
                actionStack.push(new ActionVdList(this));
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
