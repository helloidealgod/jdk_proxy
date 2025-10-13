package com.example.compile.table3.tree.action.impl;

import com.example.compile.table3.middle.Result;
import com.example.compile.table3.tree.Syntax;
import com.example.compile.table3.tree.action.Action;

import java.util.ArrayList;
import java.util.List;

import static com.example.compile.table3.middle.Utils.resultList;
import static com.example.compile.table3.operate.OprateUtils.*;
import static com.example.compile.table3.operate.OprateUtils.opStack;

public class ActionE extends Action {
    private List<Result> eResultList = new ArrayList<>();

    public ActionE(Syntax syntax) {
        super(syntax);
    }

    @Override
    public int explain(String symbol, String token) {
        if ("{Ee'}".equals(token)) {
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
            return 0;
        }
        if (null != token && token.matches("\\d*")) {
            valStack.push(new com.example.compile.table3.middle.Result("int", "", token));
        } else if (null != token && token.matches("[A-Za-z]+[A-Za-z0-9]*")) {
            valStack.push(new com.example.compile.table3.middle.Result("int", token, null));
        } else if (isOperate(token)) {
            boolean flag = true;
            while (flag) {
                //是运算符 与前一个运算符做优先级比较 高于前一个 压入操作符栈 ，低于前一个 pop 操作符栈，进行运算
                int level = 0;
                if (!opStack.isEmpty() && (level = operateCompare(opStack.getTop(), token)) <= 0) {
                    //前面运算符优先级高于或等于当前运算符优先级，pop 操作符栈 进行运算
                    if (0 == level && "(".equals(opStack.getTop())) {
                        opStack.pop();
                        flag = false;
                    } else if (0 == level && ",".equals(opStack.getTop())) {
                        opStack.push(token);
                        flag = false;
                    } else {
                        //运算
                        com.example.compile.table3.middle.Result result = operate(opStack.pop());
                        if (null != result) {
                            //结果入栈
                            valStack.push(result);
                        }
                    }
                } else {
                    //前面无运算符或当前运算符优先级高于前一个，压入操作符栈
                    opStack.push(token);
                    flag = false;
                }
            }
        }
        return 1;
    }
}