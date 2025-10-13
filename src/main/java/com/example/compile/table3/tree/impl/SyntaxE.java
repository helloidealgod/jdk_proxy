package com.example.compile.table3.tree.impl;

import com.example.compile.table3.middle.Result;
import com.example.compile.table3.tree.Syntax;
import com.example.compile.table3.tree.action.impl.ActionE;

import java.util.ArrayList;
import java.util.List;

import static com.example.compile.table3.middle.Utils.resultList;
import static com.example.compile.table3.operate.OprateUtils.*;
import static com.example.compile.table3.operate.OprateUtils.opStack;

public class SyntaxE extends Syntax {
    private List<Result> eResultList = new ArrayList<>();

    public SyntaxE() {
        super();
        actionStack.push(new ActionE(this));
    }

    @Override
    public int execute(String symbol, String token) {
        actionStack.getTop().explain(symbol, token);
        return 0;
    }

    @Override
    public int execute(String symbol) {
        if ("{Ee}".equals(symbol)) {
            //是结束符 pop 操作符栈 进行运算
            while (!opStack.isEmpty()) {
                String op = opStack.pop();
                //运算
                com.example.compile.table3.middle.Result result = operate(op);
                if (null != result) {
                    //结果入栈
                    valStack.push(result);
                }
            }
            if (!opStack.isEmpty()) {
                String pop = opStack.pop();
            }
            if (!resultList.isEmpty()) {
                com.example.compile.table3.middle.Utils.printResult();
                this.eResultList = resultList;
                resultList = new ArrayList<>();
                return 0;
            }
        }
        return 0;
    }
}
